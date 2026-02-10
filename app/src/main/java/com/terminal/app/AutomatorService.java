package com.terminal.app;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.graphics.Path;
import android.view.accessibility.AccessibilityEvent;

public class AutomatorService extends AccessibilityService {
    private static AutomatorService instance;

    @Override
    protected void onServiceConnected() { instance = this; }

    public static void tap(int x, int y) {
        if (instance == null) return;
        Path path = new Path();
        path.moveTo(x, y);
        GestureDescription.Builder gb = new GestureDescription.Builder();
        gb.addStroke(new GestureDescription.StrokeDescription(path, 0, 50));
        instance.dispatchGesture(gb.build(), null, null);
    }

    @Override public void onAccessibilityEvent(AccessibilityEvent event) {}
    @Override public void onInterrupt() {}
}

