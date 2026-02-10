package com.terminal.app;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.widget.EditText;
import android.widget.Toast;

public class Interpreter {
    private Context context;

    public Interpreter(Context context) {
        this.context = context;
    }

    public void run(String code, EditText editor) {
        String input = code.trim();
        if (input.isEmpty()) return;

        // Команда SPEEDY
        if (input.startsWith("speedy:\"Xiaomi\"")) {
            Toast.makeText(context, "Оптимизация: открываю настройки ускорения...", Toast.LENGTH_LONG).show();
            
            // 1. Открываем параметры разработчика (для 4x MSAA)
            try {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS);
                context.startActivity(intent);
            } catch (Exception e) {
                // Если не открылось, открываем общие настройки
                context.startActivity(new Intent(Settings.ACTION_SETTINGS));
            }
            return;
        }

        // Старые команды
        if (input.equalsIgnoreCase("clear")) {
            editor.setText("");
        } else if (input.startsWith("click:")) {
            handleClick(input);
        } else if (input.startsWith("message:\"")) {
            String msg = input.substring(9, input.length() - 1);
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        }
    }

    private void handleClick(String input) {
        try {
            String[] parts = input.replace("click:", "").trim().split(",");
            Intent intent = new Intent(context, AutomatorService.class);
            intent.putExtra("action", "click");
            intent.putExtra("x", Integer.parseInt(parts[0].trim()));
            intent.putExtra("y", Integer.parseInt(parts[1].trim()));
            context.startService(intent);
        } catch (Exception e) {
            Toast.makeText(context, "Ошибка click!", Toast.LENGTH_SHORT).show();
        }
    }

    public void stop() {
        Toast.makeText(context, "Остановлено", Toast.LENGTH_SHORT).show();
    }
}
