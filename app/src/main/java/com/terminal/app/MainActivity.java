package com.terminal.app;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Interpreter interpreter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Динамическое подключение макета
        int layoutId = getResources().getIdentifier("activity_main", "layout", getPackageName());
        if (layoutId != 0) {
            setContentView(layoutId);
        }

        // Инициализация интерпретатора
        interpreter = new Interpreter(this);

        // Поиск элементов
        int editorId = getResources().getIdentifier("editor", "id", getPackageName());
        int startId = getResources().getIdentifier("btnStart", "id", getPackageName());
        int stopId = getResources().getIdentifier("btnStop", "id", getPackageName());

        EditText editor = findViewById(editorId);
        View btnStart = findViewById(startId);
        View btnStop = findViewById(stopId);

        if (btnStart != null && editor != null) {
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
