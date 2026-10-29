package com.pwz.hello;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.android.gms.mlkit.vision.codescanner.GmsBarcodeScanner;
import com.google.android.gms.mlkit.vision.codescanner.GmsBarcodeScanning;

public class MainActivity extends Activity {

    private static final int IMAGE_REQUEST = 1001;
    private TextView resultText;
    private ImageView imagePreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER_HORIZONTAL);
        root.setPadding(24, 36, 24, 24);
        root.setBackgroundColor(Color.rgb(13, 29, 53));

        TextView title = new TextView(this);
        title.setText("TindaPOS Scanner");
        title.setTextSize(28);
        title.setTextColor(Color.WHITE);
        title.setGravity(Gravity.CENTER);
        title.setPadding(0, 0, 0, 12);
        root.addView(title, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView subtitle = new TextView(this);
        subtitle.setText("Scan products for your POS");
        subtitle.setTextSize(16);
        subtitle.setTextColor(Color.LTGRAY);
        subtitle.setGravity(Gravity.CENTER);
        subtitle.setPadding(0, 0, 0, 28);
        root.addView(subtitle);

        Button barcodeButton = new Button(this);
        barcodeButton.setText("BARCODE SCANNER");
        root.addView(barcodeButton, buttonParams());

        Button imageButton = new Button(this);
        imageButton.setText("PRODUCT IMAGE SCANNER");
        root.addView(imageButton, buttonParams());

        resultText = new TextView(this);
        resultText.setText("Ready to scan");
        resultText.setTextSize(17);
        resultText.setTextColor(Color.WHITE);
        resultText.setGravity(Gravity.CENTER);
        resultText.setPadding(0, 24, 0, 12);
        root.addView(resultText);

        imagePreview = new ImageView(this);
        imagePreview.setAdjustViewBounds(true);
        imagePreview.setScaleType(ImageView.ScaleType.FIT_CENTER);

        LinearLayout.LayoutParams imageParams =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, 360);
        imageParams.topMargin = 12;
        root.addView(imagePreview, imageParams);

        barcodeButton.setOnClickListener(v -> scanBarcode());
        imageButton.setOnClickListener(v -> takeProductPhoto());

        setContentView(root);
    }

    private LinearLayout.LayoutParams buttonParams() {
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 8, 0, 8);
        return params;
    }

    private void scanBarcode() {
        resultText.setText("Opening barcode scanner...");

        GmsBarcodeScanner scanner =
                GmsBarcodeScanning.getClient(this);

        scanner.startScan()
                .addOnSuccessListener(barcode -> {
                    String value = barcode.getRawValue();

                    if (value != null && !value.isEmpty()) {
                        resultText.setText(
                                "Scanned barcode:\n" + value +
                                "\n\nProduct lookup is not connected yet.");
                    } else {
                        resultText.setText("No barcode value found.");
                    }
                })
                .addOnCanceledListener(() ->
                        resultText.setText("Barcode scan cancelled."))
                .addOnFailureListener(error -> {
                    resultText.setText("Scanner could not start.");
                    Toast.makeText(this,
                            error.getLocalizedMessage() != null
                                    ? error.getLocalizedMessage()
                                    : "Check Google Play services",
                            Toast.LENGTH_LONG).show();
                });
    }

    private void takeProductPhoto() {
        Intent intent =
                new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, IMAGE_REQUEST);
        } else {
            Toast.makeText(this,
                    "No camera app is available",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST &&
                resultCode == Activity.RESULT_OK &&
                data != null && data.getExtras() != null) {

            Object photo = data.getExtras().get("data");

            if (photo instanceof Bitmap) {
                imagePreview.setImageBitmap((Bitmap) photo);
                resultText.setText(
                        "Product photo captured.\n" +
                        "Inventory image matching is not connected yet.");
            }
        }
    }
}
