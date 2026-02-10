package com.terminal.app;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.view.SoundEffectConstants; // Для звука
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Interpreter interpreter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int layoutId = getResources().getIdentifier("activity_main", "layout", getPackageName());
        if (layoutId != 0) setContentView(layoutId);

        interpreter = new Interpreter(this);

        EditText editor = findViewById(getResources().getIdentifier("editor", "id", getPackageName()));
        View btnStart = findViewById(getResources().getIdentifier("btnStart", "id", getPackageName()));
        View btnStop = findViewById(getResources().getIdentifier("btnStop", "id", getPackageName()));

        if (btnStart != null && editor != null) {
            btnStart.setOnClickListener(v -> {
                v.playSoundEffect(SoundEffectConstants.CLICK); // ЗВУК
                interpreter.run(editor.getText().toString(), editor);
            });
        }

        if (btnStop != null) {
            btnStop.setOnClickListener(v -> {
                v.playSoundEffect(SoundEffectConstants.CLICK); // ЗВУК
                interpreter.stop();
            });
        }
    }
}
