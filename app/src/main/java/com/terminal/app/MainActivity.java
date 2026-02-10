package com.terminal.app;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    // Interpreter interpreter; // Раскомментируй, когда файл будет готов

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Находим ID макета activity_main динамически без R.layout
        int layoutId = getResources().getIdentifier("activity_main", "layout", getPackageName());
        setContentView(layoutId);

        // Находим элементы по их текстовым ID
        EditText editor = findViewById(resId("editor"));
        View btnStart = findViewById(resId("btnStart"));
        View btnStop = findViewById(resId("btnStop"));

        if (btnStart != null) {
            btnStart.setOnClickListener(v -> {
                if (editor != null) {
                    String code = editor.getText().toString();
                    // interpreter.run(code);
                }
            });
        }

        if (btnStop != null) {
            btnStop.setOnClickListener(v -> {
                // interpreter.stop();
            });
        }
    }

    // Вспомогательная функция, чтобы не писать R.id
    private int resId(String name) {
        return getResources().getIdentifier(name, "id", getPackageName());
    }
}
