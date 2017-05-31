package io.github.projektmedinf.wifisdcryptolocker.activities;

import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.TextView;
import io.github.projektmedinf.wifisdcryptolocker.R;

public class ImageDetailsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);

        String title = getIntent().getStringExtra("title");

        TextView titleTextView = (TextView) findViewById(R.id.details_title);
        titleTextView.setText(title);

        ImageView imageView = (ImageView) findViewById(R.id.details_image);
        TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(Integer.parseInt(title), -1));
        imageView.setImageBitmap(bitmap);
    }
}