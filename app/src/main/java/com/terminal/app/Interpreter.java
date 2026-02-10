package com.terminal.app;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.EditText;
import androidx.core.app.NotificationCompat;

public class Interpreter {
    private Context context;
    private static final String CHANNEL_ID = "terminal_notif";

    public Interpreter(Context context) {
        this.context = context;
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID, "Terminal Channel", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    public void run(String code, EditText editor) {
        String input = code.trim();

        // НОВАЯ КОМАНДА: notify:"заголовок","текст"
        if (input.startsWith("notify:\"")) {
            try {
                String content = input.substring(8, input.length() - 1);
                String[] parts = content.split("\",\"");
                sendNotification(parts[0], parts[1]);
            } catch (Exception e) {
                sendNotification("Terminal", "Ошибка формата уведомления");
            }
            return;
        }

        // КЛИК (обычный)
        if (input.startsWith("click:")) {
            // Код отправки в AutomatorService как раньше
            handleClick(input);
            return;
        }
        
        // ОЧИСТКА
        if (input.equalsIgnoreCase("clear")) {
            editor.setText("");
        }
    }

    private void sendNotification(String title, String text) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, builder.build());
    }

    private void handleClick(String input) {
        String[] parts = input.replace("click:", "").trim().split(",");
        Intent intent = new Intent(context, AutomatorService.class);
        intent.putExtra("action", "click"); // Важно: action "click" для одного нажатия
        intent.putExtra("x", Integer.parseInt(parts[0].trim()));
        intent.putExtra("y", Integer.parseInt(parts[1].trim()));
        context.startService(intent);
    }

    public void stop() { /* стоп логика */ }
    }
