package com.pwz.hello;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView text = new TextView(this);
        text.setText("Hello from PwZ!\n\nMy first Android APK");
        text.setTextSize(26);
        text.setTextColor(Color.WHITE);
        text.setGravity(Gravity.CENTER);
        text.setBackgroundColor(Color.rgb(25, 45, 75));

        setContentView(text);
    }
}
