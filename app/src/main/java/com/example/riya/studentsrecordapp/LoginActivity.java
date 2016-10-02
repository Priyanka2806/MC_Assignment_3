package com.example.riya.studentsrecordapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";
    private static final String PREF_NAME = "myDetails";

    private EditText txtName;
    private EditText txtCourse;
    private EditText txtSubject;
    private EditText txtEmailId;
    private Button btnSave;
    private Switch loginSwitch;

    FileOutputStream fos;
    String savedName= null;

    public final static String EXTRA_MESSAGE = "MyName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnSave = (Button) findViewById(R.id.btnSave);
        SharedPreferences value = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        savedName = value.getString("UserName", null);
        if(savedName!=null) {
            String message = savedName;
            Intent intent = new Intent(getApplicationContext(), StudentActivity.class);
            intent.putExtra(EXTRA_MESSAGE, message);
            startActivityForResult(intent,1);
        }
        else {
            btnSave.setText("Save");
        }


        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                txtName = (EditText) findViewById(R.id.txtName);
                txtCourse = (EditText) findViewById(R.id.txtCourse);
                txtSubject= (EditText) findViewById(R.id.txtSubject);
                txtEmailId = (EditText) findViewById(R.id.txtEmailId);

                String name = txtName.getText().toString();
                String course = txtCourse.getText().toString();
                String subject = txtSubject.getText().toString();
                String email = txtEmailId.getText().toString();

                //Writing details in shared preferences.
                loginSwitch = (Switch)findViewById(R.id.switch1);
                if(loginSwitch.isChecked())
                {
                    SharedPreferences data = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = data.edit();
                    editor.clear();
                    editor.putString("UserName", name);
                    editor.commit();
                }
                //Writing details in internal storage
                String FILENAME = "user_file";

                try {
                    fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
                    fos.write(name.getBytes());
                    fos.write(course.getBytes());
                    fos.write(subject.getBytes());
                    fos.write(email.getBytes());

                    fos.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                Toast.makeText(getApplicationContext(), "Details Saved!!", Toast.LENGTH_LONG).show();
                String message = name;
                Intent intent = new Intent(getApplicationContext(), StudentActivity.class);
                intent.putExtra(EXTRA_MESSAGE, message);
                startActivityForResult(intent,1);

            }
        });

        if(savedInstanceState!=null)
        {
            //If the activity is being restored, the saved state of activity is taken to recreated it.
            txtName = (EditText) findViewById(R.id.txtName);
            txtCourse = (EditText) findViewById(R.id.txtCourse);
            txtSubject= (EditText) findViewById(R.id.txtSubject);
            txtEmailId = (EditText) findViewById(R.id.txtEmailId);
            txtName.setText(savedInstanceState.getString("namefield"));
            txtCourse.setText(savedInstanceState.getString("coursefield"));
            txtSubject.setText(savedInstanceState.getString("subjectfield"));
            txtEmailId.setText(savedInstanceState.getString("idfield"));

        }
        else{
            txtName = (EditText) findViewById(R.id.txtName);
            txtCourse = (EditText) findViewById(R.id.txtCourse);
            txtSubject= (EditText) findViewById(R.id.txtSubject);
            txtEmailId = (EditText) findViewById(R.id.txtEmailId);
            txtName.setText("");
            txtCourse.setText("");
            txtSubject.setText("");
            txtEmailId.setText("");

        }

    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("namefield", txtName.getText().toString());
        savedInstanceState.putString("coursefield", txtCourse.getText().toString());
        savedInstanceState.putString("subjectfield", txtSubject.getText().toString());
        savedInstanceState.putString("idfield", txtEmailId.getText().toString());

        //To save current state details of the activity.
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                    String result=data.getStringExtra("backResult");
                if(result.equals("out")){

                    SharedPreferences preferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                    preferences.edit().remove("UserName").commit();

                    txtName = (EditText) findViewById(R.id.txtName);
                    txtCourse = (EditText) findViewById(R.id.txtCourse);
                    txtSubject= (EditText) findViewById(R.id.txtSubject);
                    txtEmailId = (EditText) findViewById(R.id.txtEmailId);
                    txtName.setText("");
                    txtCourse.setText("");
                    txtSubject.setText("");
                    txtEmailId.setText("");
                }
            }
        }
    }


}

