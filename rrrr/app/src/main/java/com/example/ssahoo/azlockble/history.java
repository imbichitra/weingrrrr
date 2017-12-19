package com.example.ssahoo.azlockble;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class history extends AppCompatActivity {
    TextView lv;
    private String filename = "SampleFile.txt";
    private String filepath = "MyFileStorage";
    File myExternalFile;
    String myData = "  ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        lv = (TextView) findViewById(R.id.textView4);
        myExternalFile = new File(getExternalFilesDir(filepath),filename);
        try {
            FileInputStream fis = new FileInputStream(myExternalFile);
            DataInputStream in = new DataInputStream(fis);
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(in));
            String strLine;
            while ((strLine = br.readLine())!= null) {
                myData = myData + "\t" +   strLine   + "\n" ;

            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        lv.setText(myData);

    }
}


