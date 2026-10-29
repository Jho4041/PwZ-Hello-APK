package com.pwz.hello;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER);
        root.setPadding(28, 32, 28, 32);
        root.setBackgroundColor(Color.rgb(13, 29, 53));

        TextView logo = new TextView(this);
        logo.setText("PwZ");
        logo.setTextSize(34);
        logo.setTypeface(Typeface.DEFAULT_BOLD);
        logo.setTextColor(Color.WHITE);
        logo.setGravity(Gravity.CENTER);

        GradientDrawable circle = new GradientDrawable();
        circle.setShape(GradientDrawable.OVAL);
        circle.setColor(Color.rgb(0, 150, 210));
        logo.setBackground(circle);

        LinearLayout.LayoutParams logoParams =
            new LinearLayout.LayoutParams(120, 120);
        logoParams.bottomMargin = 32;
        root.addView(logo, logoParams);

        TextView title = new TextView(this);
        title.setText("Welcome to PwZ App");
        title.setTextSize(27);
        title.setTypeface(Typeface.DEFAULT_BOLD);
        title.setTextColor(Color.WHITE);
        title.setGravity(Gravity.CENTER);
        root.addView(title);

        TextView subtitle = new TextView(this);
        subtitle.setText("Your first Android app, built with GitHub");
        subtitle.setTextSize(16);
        subtitle.setTextColor(Color.LTGRAY);
        subtitle.setGravity(Gravity.CENTER);
        subtitle.setPadding(0, 16, 0, 32);
        root.addView(subtitle);

        Button button = new Button(this);
        button.setText("GET STARTED");
        root.addView(button);

        button.setOnClickListener(v ->
            Toast.makeText(this, "PwZ App is working!",
                Toast.LENGTH_SHORT).show()
        );

        setContentView(root);
    }
}
