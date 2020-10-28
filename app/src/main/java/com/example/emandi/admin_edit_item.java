package com.example.emandi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;

public class admin_edit_item extends AppCompatActivity {
    ImageView img ;
    Button retake , save ;
    TextInputEditText title , rs, unit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_item);

        img = findViewById(R.id.editimage);
        retake = findViewById(R.id.editpic);
        save = findViewById(R.id.saveitem);
        title = findViewById(R.id.edittitle);
        rs = findViewById(R.id.costRs);
        unit = findViewById(R.id.costUnit);
        Intent i = getIntent();
        if(i.hasExtra("ID")){
            title.setText(i.getStringExtra("title"));
            String cost = i.getStringExtra("cost");
            String Rs = cost.substring(0,cost.indexOf("R")).trim();
            String Unit = cost.substring(cost.indexOf("/")+1).trim();
            rs.setText(Rs);
            unit.setText(Unit);
        }else{
            retake.setText("Take Picture");
            save.setText("Add Item");
        }
        retake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i,0);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
        img.setImageBitmap(bitmap);
    }
}