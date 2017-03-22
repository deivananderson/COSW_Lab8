package com.edu.eci.cosw.controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.eci.cosw.lab08.R;
import com.edu.eci.cosw.model.Post;
import com.edu.eci.cosw.model.Encoding;

public class PostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        Intent intent = getIntent();
        Post message = (Post) intent.getSerializableExtra(MainActivity.EXTRA_MESSAGE);

        TextView textView = new TextView(this);
        textView.setText(message.getText());

        ImageView imageView = new ImageView(this);

        String photo = message.getPhoto();
        Bitmap bitmap = Encoding.decodeBase64(photo);
        imageView.setImageBitmap(bitmap);

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_display_message);
        layout.addView(textView);

        ViewGroup layoutImg = (ViewGroup) findViewById(R.id.activity_display_image);
        layoutImg.addView(imageView);
    }
}
