package com.terminal.app;

import android.os.Bundle;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Interpreter interpreter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        interpreter = new Interpreter(this);
        EditText editor = findViewById(R.id.editor);

        // Кнопка >
        findViewById(R.id.btnStart).setOnClickListener(v -> {
            interpreter.run(editor.getText().toString());
        });

        // Кнопка ||
        findViewById(R.id.btnStop).setOnClickListener(v -> {
            interpreter.stop();
        });
    }
}

