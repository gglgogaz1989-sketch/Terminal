package com.terminal.app;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
// УБЕРИ импорт com.terminal.app.R, если он там есть

public class MainActivity extends AppCompatActivity {
    // Если Interpreter еще не создан или в нем ошибки, 
    // давай временно его закомментируем, чтобы проверить только интерфейс
    // Object interpreter; 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // R должен подхватиться автоматически, так как файл в том же пакете
        setContentView(R.layout.activity_main);
        
        EditText editor = findViewById(R.id.editor);

        findViewById(R.id.btnStart).setOnClickListener(v -> {
            // interpreter.run(...)
        });

        findViewById(R.id.btnStop).setOnClickListener(v -> {
            // interpreter.stop()
        });
    }
}
