package com.terminal.app;

import android.content.Context;
import android.widget.Toast;

public class Interpreter {
    private Context context;

    public Interpreter(Context context) {
        this.context = context;
    }

    public void run(String code) {
        // Чтобы проверить, что работает, выведем текст на экран
        Toast.makeText(context, "Команда получена: " + code, Toast.LENGTH_SHORT).show();
    }

    public void stop() {
        Toast.makeText(context, "Остановлено", Toast.LENGTH_SHORT).show();
    }
}
