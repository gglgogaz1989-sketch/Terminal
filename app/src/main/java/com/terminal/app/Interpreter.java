package com.terminal.app;

import android.content.Context;
import android.content.Intent;
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

        // 1. Команда MESSAGE (message:"текст")
        if (input.startsWith("message:\"") && input.endsWith("\"")) {
            String msg = input.substring(9, input.length() - 1);
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
            return;
        }

        // 2. Команда CLEAR (чистит поле)
        if (input.equalsIgnoreCase("clear")) {
            editor.setText("");
            return;
        }

        // 3. Команда CLICK (click: 500, 1000)
        if (input.startsWith("click:")) {
            try {
                String[] parts = input.replace("click:", "").trim().split(",");
                int x = Integer.parseInt(parts[0].trim());
                int y = Integer.parseInt(parts[1].trim());

                // Отправляем координаты в наш AutomatorService
                Intent intent = new Intent(context, AutomatorService.class);
                intent.putExtra("action", "click");
                intent.putExtra("x", x);
                intent.putExtra("y", y);
                context.startService(intent);

            } catch (Exception e) {
                Toast.makeText(context, "Ошибка формата! Пример: click: 500, 1000", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        Toast.makeText(context, "Неизвестно: " + input, Toast.LENGTH_SHORT).show();
    }
}
