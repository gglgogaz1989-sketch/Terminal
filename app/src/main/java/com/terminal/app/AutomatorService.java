package com.terminal.app;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;
import android.content.Intent;
import android.graphics.Color;
import android.view.accessibility.AccessibilityEvent;
import android.os.Handler;
import android.os.Looper;

public class AutomatorService extends AccessibilityService {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && "click".equals(intent.getStringExtra("action"))) {
            int x = intent.getIntExtra("x", 0);
            int y = intent.getIntExtra("y", 0);
            
            click(x, y);
            showVisualMarker(x, y);
        }
        return START_STICKY;
    }

    private void click(int x, int y) {
        Path path = new Path();
        path.moveTo(x, y);
        GestureDescription.Builder builder = new GestureDescription.Builder();
        builder.addStroke(new GestureDescription.StrokeDescription(path, 0, 50));
        dispatchGesture(builder.build(), null, null);
    }

    private void showVisualMarker(int x, int y) {
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        TextView marker = new TextView(this);
        marker.setText("+");
        marker.setTextColor(Color.RED);
        marker.setTextSize(30);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY, // Важно для отображения везде
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        
        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = x - 20; // центрируем плюс
        params.y = y - 50;

        wm.addView(marker, params);

        // Убираем плюс через 1 секунду
        new Handler(Looper.getMainLooper()).postDelayed(() -> wm.removeView(marker), 1000);
    }

    @Override public void onAccessibilityEvent(AccessibilityEvent event) {}
    @Override public void onInterrupt() {}
}
