package com.terminal.app;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Interpreter interpreter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Пытаемся найти макет
        int layoutId = getResources().getIdentifier("activity_main", "layout", getPackageName());
        
        if (layoutId == 0) {
            Toast.makeText(this, "КРИТИЧЕСКАЯ ОШИБКА: activity_main.xml не найден!", Toast.LENGTH_LONG).show();
            return;
        }
        
        setContentView(layoutId);

        // 2. Инициализируем мозги
        interpreter = new Interpreter(this);

        // 3. Ищем кнопки по именам
        int edId = getResources().getIdentifier("editor", "id", getPackageName());
        int bStartId = getResources().getIdentifier("btnStart", "id", getPackageName());
        int bStopId = getResources().getIdentifier("btnStop", "id", getPackageName());

        EditText editor = findViewById(edId);
        View btnStart = findViewById(bStartId);
        View btnStop = findViewById(bStopId);

        // 4. Проверяем, нашлись ли они
        if (btnStart == null || editor == null) {
            Toast.makeText(this, "ОШИБКА: Кнопки не найдены в XML!", Toast.LENGTH_LONG).show();
        } else {
            btnStart.setOnClickListener(v -> {
                String code = editor.getText().toString();
                interpreter.run(code);
            });
        }

        if (btnStop != null) {
            btnStop.setOnClickListener(v -> interpreter.stop());
        }
    }
}
