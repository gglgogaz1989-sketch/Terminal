package com.terminal.app;

import android.os.Bundle;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
// Добавляем явный импорт твоего R-класса
import com.terminal.app.R;

public class MainActivity extends AppCompatActivity {
    Interpreter interpreter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Используем полный путь, чтобы компилятор не сомневался
        setContentView(com.terminal.app.R.layout.activity_main);
        
        interpreter = new Interpreter(this);
        EditText editor = findViewById(com.terminal.app.R.id.editor);

        // Кнопка ПУСК
        findViewById(com.terminal.app.R.id.btnStart).setOnClickListener(v -> {
            interpreter.run(editor.getText().toString());
        });

        // Кнопка СТОП
        findViewById(com.terminal.app.R.id.btnStop).setOnClickListener(v -> {
            interpreter.stop();
        });
    }
}
