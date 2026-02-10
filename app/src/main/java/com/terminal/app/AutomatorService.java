package com.terminal.app;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

public class AutomatorService extends AccessibilityService {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) return START_STICKY;

        String action = intent.getStringExtra("action");
        int x = intent.getIntExtra("x", 0);
        int y = intent.getIntExtra("y", 0);

        if ("click".equals(action)) {
            performSingleClick(x, y);
            showVisualMarker(x, y); // РИСУЕМ ПЛЮСИК ТУТ
        }
        // ... тут может быть код для автокликера start_loop ...

        return START_STICKY;
    }

    private void showVisualMarker(int x, int y) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> {
            WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
            TextView marker = new TextView(this);
            marker.setText("+");
            marker.setTextColor(Color.RED);
            marker.setTextSize(24);
            marker.setShadowLayer(5, 0, 0, Color.BLACK);

            WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY, // Чтобы было поверх всех окон
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);

            params.gravity = Gravity.TOP | Gravity.LEFT;
            params.x = x - 20; // Корректировка, чтобы центр плюса совпал с кликом
            params.y = y - 60;

            wm.addView(marker, params);

            // Удаляем через 800мс
            handler.postDelayed(() -> {
                try { wm.removeView(marker); } catch (Exception e) {}
            }, 800);
        });
    }

    private void performSingleClick(int x, int y) {
        Path path = new Path();
        path.moveTo(x, y);
        GestureDescription.Builder builder = new GestureDescription.Builder();
        builder.addStroke(new GestureDescription.StrokeDescription(path, 0, 50));
        dispatchGesture(builder.build(), null, null);
    }

    @Override public void onAccessibilityEvent(android.view.accessibility.AccessibilityEvent event) {}
    @Override public void onInterrupt() {}
}
