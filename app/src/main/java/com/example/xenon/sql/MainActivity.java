package com.example.xenon.sql;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Button bload;
    private Button bsave;
    private EditText ed1;
    private EditText ed2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DBadapter ad=new DBadapter(getApplicationContext());

        bsave=(Button)findViewById(R.id.button);
        bload=(Button)findViewById(R.id.button2);

        ed1=(EditText)findViewById(R.id.editText);
        ed1=(EditText)findViewById(R.id.editText3);

        bsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double n= Double.parseDouble(ed2.getText().toString());
                ad.insertPhone(n, ed1.getText().toString());
            }
        });

        bload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = ad.getAllData();
            }
        });
    }
}
