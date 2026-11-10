package com.rafan.goodcharades;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity implements SensorEventListener {
    private WebView web;
    private SensorManager sensorManager;
    private Sensor accelerometer;

    // Orientation-independent nod detection.
    // The phone may be held portrait or landscape (either side), so instead of
    // assuming one axis, we track the accelerometer's "up" vector: at rest on
    // a forehead it points up along the screen, carried by whichever device
    // axes (x and y) are dominant. A nod rotates that vector out of the screen
    // plane: forehead tips forward (screen toward the floor) and its z
    // component goes negative; tips back (screen toward the ceiling) and it
    // goes positive. The angle of that rotation is the nod angle, and it works
    // identically in every holding orientation.
    private float gx, gy, gz;
    private boolean haveGravity;
    private float baseTheta = Float.NaN;
    private int warmSamples = 0;
    private long stableSinceMs = 0;
    private float stableTheta = Float.NaN;
    // Gesture state machine: a nod must start from a settled neutral and the
    // detector stays LOCKED after firing until the phone has fully returned
    // to neutral and settled there. Without the lock, the swing back from a
    // down-nod overshoots past neutral and registers as a phantom "up" —
    // the next word gets passed while the player is just straightening up.
    private boolean tiltLocked = false;
    private long firedMs = 0;
    private long neutralSinceMs = 0;
    private int overSamples = 0;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        enterImmersive();
        web = new WebView(this);
        web.setBackgroundColor(Color.rgb(12, 11, 14));
        WebSettings s = web.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setMediaPlaybackRequiresUserGesture(false);
        s.setLoadWithOverviewMode(true);
        s.setUseWideViewPort(true);
        // The page is a bundled local asset and needs nothing beyond it:
        // file access stays on for android_asset, everything else is shut.
        s.setAllowFileAccess(true);
        s.setAllowContentAccess(false);
        s.setGeolocationEnabled(false);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        web.setWebViewClient(new WebViewClient() {
            @Override public void onPageFinished(WebView view, String url) {
                // Tell the page whether the native tilt source exists. If the
                // device has no accelerometer, the page re-enables its own
                // deviceorientation fallback instead of tilt being silently dead.
                view.evaluateJavascript(
                    "if(window.__setNativeTilt)window.__setNativeTilt(" + (accelerometer != null) + ")", null);
            }
        });
        web.loadUrl("file:///android_asset/index.html");
        setContentView(web);
    }

    private void enterImmersive() {
        if (Build.VERSION.SDK_INT >= 30) {
            getWindow().setDecorFitsSystemWindows(false);
            WindowInsetsController c = getWindow().getInsetsController();
            if (c != null) {
                c.hide(WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars());
                c.setSystemBarsBehavior(WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
            }
        } else {
            getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN |
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

    @Override public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) enterImmersive();
    }

    @Override protected void onResume() {
        super.onResume();
        if (web != null) web.onResume();
        if (sensorManager != null && accelerometer != null)
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override protected void onPause() {
        if (sensorManager != null) sensorManager.unregisterListener(this);
        if (web != null) web.onPause();
        super.onPause();
    }

    @Override protected void onDestroy() {
        if (web != null) { web.destroy(); web = null; }
        super.onDestroy();
    }

    // Back is the game's button first: it cancels a countdown, pauses or
    // resumes a round, and leaves sub-screens — only on the home screen does
    // it fall through to leaving the app.
    @Override public void onBackPressed() {
        if (web != null) {
            web.evaluateJavascript("window.__gcaBack?window.__gcaBack():false", v -> {
                if (!"true".equals(v)) MainActivity.super.onBackPressed();
            });
        } else {
            super.onBackPressed();
        }
    }

    private static float wrapDeg(float a) {
        while (a > 180f) a -= 360f;
        while (a < -180f) a += 360f;
        return a;
    }

    @Override public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER || web == null) return;
        float x = event.values[0], y = event.values[1], z = event.values[2];

        // Low-pass filter isolates gravity from hand shake / movement noise.
        // 0.6 (was 0.75) tracks the nod faster, so the effective fire angle
        // sits much closer to the coded 24 degrees at normal nod speeds.
        if (!haveGravity) { gx = x; gy = y; gz = z; haveGravity = true; }
        else {
            float a = 0.6f;
            gx = a * gx + (1 - a) * x;
            gy = a * gy + (1 - a) * y;
            gz = a * gz + (1 - a) * z;
        }
        float mag = (float) Math.sqrt(gx * gx + gy * gy + gz * gz);
        if (mag < 6.5f || mag > 13.5f) return; // free-fall or hard shake: ignore

        // The accelerometer reports the "up" vector (opposite of gravity's
        // pull). At rest on a forehead it points up along the screen. Using
        // the true in-plane magnitude sqrt(gx^2+gy^2) — not max(|gx|,|gy|) —
        // keeps the nod angle identical in portrait, landscape and diagonal
        // holds; max() compressed the angle on diagonal holds, so the same
        // nod fired in one orientation and missed in another.
        float along = (float) Math.sqrt(gx * gx + gy * gy); // in-plane up component, >= 0 at rest
        if (along < 4.5f) return; // phone held nearly flat: nods are unreadable

        // Nod angle: 0 at rest. Tipping forward (screen toward the floor)
        // drives the up vector's z component negative, so theta goes negative.
        float theta = (float) Math.toDegrees(Math.atan2(gz, along));
        long now = System.currentTimeMillis();

        // Warm-up: the first samples after launch only build the baseline,
        // averaged, so picking the phone up already tilted can't define a
        // lopsided "neutral" that fires a phantom on the first movement.
        if (Float.isNaN(baseTheta)) { baseTheta = theta; warmSamples = 1; return; }
        if (warmSamples < 15) {
            warmSamples++;
            baseTheta += 0.34f * wrapDeg(theta - baseTheta);
            return;
        }
        float d = wrapDeg(theta - baseTheta);

        if (tiltLocked) {
            // A gesture just fired. The return swing — including its natural
            // overshoot past neutral — must never register as a new gesture.
            // Unlock only after the phone is back near neutral AND has
            // settled there briefly. Baseline stays frozen meanwhile, so the
            // overshoot is measured against the true neutral, not a drifted one.
            if (Math.abs(d) < 6f) {
                if (neutralSinceMs == 0) neutralSinceMs = now;
                if (now - neutralSinceMs > 220 && now - firedMs > 500) {
                    tiltLocked = false;
                    neutralSinceMs = 0;
                    overSamples = 0;
                }
            } else {
                neutralSinceMs = 0;
            }
            return;
        }

        // Reposition adoption: if the phone settles at a clearly different
        // angle and stays there (~1.5 s within a few degrees), that angle is
        // the player's new neutral — e.g. picking the phone up off a table
        // and putting it on their forehead during the countdown. Without
        // this, the first settle at the forehead reads as a huge deviation
        // from the table angle and fires a phantom tilt. Never while locked,
        // so a held nod can never re-baseline mid-gesture.
        if (Math.abs(d) > 12f) {
            if (Float.isNaN(stableTheta) || Math.abs(wrapDeg(theta - stableTheta)) > 3.5f) {
                stableTheta = theta;
                stableSinceMs = now;
            } else if (now - stableSinceMs > 1500) {
                baseTheta = theta;
                stableTheta = Float.NaN;
                stableSinceMs = 0;
                overSamples = 0;
                return;
            }
        } else {
            stableTheta = Float.NaN;
            stableSinceMs = 0;
        }

        if (Math.abs(d) < 6f) {
            // Near neutral: track slow drift of the holding angle. The rate
            // is deliberately slow (0.05, was 0.12 over a wider 9-degree
            // band): a gentle 2-second nod used to be chased by the baseline
            // and absorbed entirely; now it outruns the tracker and fires.
            baseTheta += 0.05f * wrapDeg(theta - baseTheta);
            overSamples = 0;
            return;
        }
        // Fire only on a deviation that is clearly a nod and is sustained
        // for a couple of samples, so a single jolt can't fake a gesture.
        if (Math.abs(d) > 24f) {
            overSamples++;
            if (overSamples >= 2) {
                tiltLocked = true;
                firedMs = now;
                overSamples = 0;
                stableTheta = Float.NaN;
                stableSinceMs = 0;
                // theta negative = screen tipped toward the floor = tilt DOWN.
                // theta positive = screen tipped toward the ceiling = tilt UP.
                String dir = d < 0 ? "down" : "up";
                web.evaluateJavascript("if(window.handleNativeTilt)window.handleNativeTilt('" + dir + "')", null);
            }
        } else {
            overSamples = 0;
        }
    }

    @Override public void onAccuracyChanged(Sensor sensor, int accuracy) { }
}
