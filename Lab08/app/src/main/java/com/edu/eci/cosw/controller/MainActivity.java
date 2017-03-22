package com.edu.eci.cosw.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.edu.eci.cosw.lab08.R;
import com.edu.eci.cosw.model.Post;
import com.edu.eci.cosw.model.Encoding;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.edu.eci.cosw.controller.MainActivity.MESSAGE";

    private static final String TAKE_PHOTO = "Take Photo";
    private static final int TAKE_PHOTO_OPTION = 1;
    private static final String CHOOSE_GALLERY = "Choose from Gallery";
    private static final int CHOOSE_GALLERY_OPTION = 2;
    private static final String CANCEL = "Cancel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addPhoto(View view) {
        final CharSequence[] options = {TAKE_PHOTO, CHOOSE_GALLERY, CANCEL};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Select or take a new Picture");
        String [] list = {"Camera","Galery"};
        builder.setItems(list, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==0){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, TAKE_PHOTO_OPTION);
                }
                else{
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), CHOOSE_GALLERY_OPTION);
                }
            }
        });
        builder.create();
        builder.show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView image = (ImageView) findViewById(R.id.imageView);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TAKE_PHOTO_OPTION:
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    image.setImageBitmap(photo);
                    break;
                case CHOOSE_GALLERY_OPTION:
                    Uri selectedImageUri = data.getData();
                    if (null != selectedImageUri) {
                        String path = getPathFromURI(selectedImageUri);
                        image.setImageURI(selectedImageUri);
                    }
                    break;
            }
        }
    }
    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    public void save(View view) {
        EditText editText = (EditText) findViewById(R.id.editText);
        ImageView image = (ImageView) findViewById(R.id.imageView);
        if (editText.length() > 0) {
            if (editText.length() <= 50 ) {
                editText.setError("Please enter either a message or select an image");
            } else {
                Intent intent = new Intent(this, PostActivity.class);
                Post post = new Post();
                post.setText(editText.getText().toString());

                Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
                String photo = Encoding.encodeToBase64(bitmap, Bitmap.CompressFormat.PNG, 0);
                post.setPhoto(photo);

                intent.putExtra(EXTRA_MESSAGE, post);
                startActivity(intent);
            }
        } else {
            editText.setError("Text can not be empty");
        }
    }
}
