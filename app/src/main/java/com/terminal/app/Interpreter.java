package com.terminal.app;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import java.util.List;

public class Interpreter {
    private Context context;
    private boolean isRunning = false;
    private int loopPointer = -1;

    public Interpreter(Context context) { this.context = context; }

    public void run(String script) {
        isRunning = true;
        String[] lines = script.split("\n");

        new Thread(() -> {
            for (int i = 0; i < lines.length; i++) {
                if (!isRunning) break;
                String cmd = lines[i].trim();

                if (cmd.startsWith("openapp:")) {
                    String name = cmd.split("\"")[1];
                    launch(name);
                } else if (cmd.startsWith("click:")) {
                    String[] parts = cmd.replace("click:", "").replace("\"", "").split(",");
                    AutomatorService.tap(Integer.parseInt(parts[1]), Integer.parseInt(parts[0]));
                } else if (cmd.startsWith("wait:")) {
                    int ms = Integer.parseInt(cmd.split("\"")[1]);
                    try { Thread.sleep(ms); } catch (Exception e) {}
                } else if (cmd.equals("loop(true)")) {
                    loopPointer = i;
                } else if (cmd.equals("back_loop") && loopPointer != -1) {
                    i = loopPointer; // Возвращаемся к началу цикла
                } else if (cmd.equals("clearmb")) {
                    handleClearMB();
                }
            }
        }).start();
    }

    public void stop() { isRunning = false; }

    private void launch(String appName) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(getPackage(appName));
        if (intent != null) context.startActivity(intent);
    }

    private String getPackage(String name) {
        List<ApplicationInfo> apps = context.getPackageManager().getInstalledApplications(0);
        for (ApplicationInfo app : apps) {
            if (app.loadLabel(context.getPackageManager()).toString().equalsIgnoreCase(name)) return app.packageName;
        }
        return "";
    }

    private void handleClearMB() {
        // Здесь логика вывода вопроса yes/no в консоль
        System.out.println("Clean unused apps? (yes/no)");
    }
}

