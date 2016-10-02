package com.example.riya.studentsrecordapp;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;


public class StudentActivity extends AppCompatActivity {

    private TextView myName;
    private TextView studentRecord;
    //private TextView studentCount;
    private EditText studentName;
    private EditText studentRollNo;
    private EditText studentMarks;
    private RatingBar studentRating;
    private Button submitButton;
    private Button logoutButton;
    private Button delButton;
    private Button updateButton;
    private TextView lblText;
    private TextView lblText2;
    private EditText edittext;
    private EditText edittext2;
    private Button okButton;
    private Button saveToFileButton;

    StudentsRecordDB recordDb;

   // SQLiteDatabase db;
    //private static final String PREF_COUNT = "countDetails";
    static int counter = 0;
    int flag = 0;
    String name;
    String rollno;
    String marks;
    int rating=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        recordDb = new StudentsRecordDB(this);
        Intent intent = getIntent();
        String message = intent.getStringExtra(LoginActivity.EXTRA_MESSAGE);
        myName = (TextView) findViewById(R.id.textView);
        myName.setText("Welcome " + message);

        studentRecord = (TextView) findViewById(R.id.studentView);
        studentRecord.setText("STUDENTS RECORD");

        //SharedPreferences spref = getSharedPreferences(PREF_COUNT, Context.MODE_PRIVATE);
        //int count = spref.getInt("countValue", 0);
        //studentCount = (TextView) findViewById(R.id.textcount);
        //studentCount.setText(count);

        submitButton = (Button) findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                ++counter;
                studentName = (EditText) findViewById(R.id.sNameView);
                studentRollNo = (EditText) findViewById(R.id.sRollView);
                studentMarks = (EditText) findViewById(R.id.sMarksView);
                studentRating = (RatingBar) findViewById(R.id.ratingBar);

                name = studentName.getText().toString();
                rollno = studentRollNo.getText().toString();
                marks = studentMarks.getText().toString();
                rating = studentRating.getNumStars();


                recordDb.insertStudent(rollno, name, marks, rating);
                //SharedPreferences sp = getSharedPreferences(PREF_COUNT, Context.MODE_PRIVATE);
                //SharedPreferences.Editor edit = sp.edit();
                //edit.clear();
                //edit.putInt("countValue", counter);
                //edit.commit();
                Toast.makeText(getApplicationContext(), "Data Entered!!", Toast.LENGTH_LONG).show();
                clearFieldsForNewEntry();
            }
        });

        logoutButton = (Button) findViewById(R.id.signout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String val = "out";
                Intent data = new Intent();
                data.putExtra("backResult",val);
                setResult(Activity.RESULT_OK, data);
                finish();

            }
        });

        lblText = (TextView)findViewById(R.id.textView2);
        delButton = (Button) findViewById(R.id.del_button);
        delButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                flag = 1;
                lblText.setText("Enter the Roll No: ");
                //edittext=(EditText)findViewById(R.id.editText1);
                //new_rollno = edittext.getText().toString();


            }
        });


        lblText2 = (TextView)findViewById(R.id.textView3);
        updateButton = (Button) findViewById(R.id.update_button);
        updateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                flag=0;
                //lblText = (TextView)findViewById(R.id.textView2);
                lblText.setText("Enter the Roll No: ");


                lblText2.setText("Enter new Marks: ");
            }
        });


        okButton = (Button)findViewById(R.id.ok_button);
        okButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(flag == 1){
                    edittext=(EditText)findViewById(R.id.editText1);
                    String new_rollno = edittext.getText().toString();
                    recordDb.deleteStudent(new_rollno);
                    //System.out.println("new values----------" + new_rollno);
                    Toast.makeText(getApplicationContext(), "Record Deleted!!", Toast.LENGTH_LONG).show();
                    edittext.setText("");
                    lblText.setText("");

                }
                else if(flag == 0){
                    edittext=(EditText)findViewById(R.id.editText1);
                    String new_rollno = edittext.getText().toString();
                    edittext2=(EditText)findViewById(R.id.editText2);
                    String  newMarks = edittext2.getText().toString();
                    //System.out.println("new values----------" + new_rollno +"andddddddddddddddd" + newMarks);
                    boolean res = recordDb.updateStudent(new_rollno, newMarks);
                    Toast.makeText(getApplicationContext(), "Marks Updated!!", Toast.LENGTH_LONG).show();
                    edittext.setText("");
                    edittext2.setText("");
                    lblText.setText("");
                    lblText2.setText("");
                }

            }
        });

        saveToFileButton = (Button)findViewById(R.id.button_file);
        saveToFileButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                studentName = (EditText) findViewById(R.id.sNameView);
                studentRollNo = (EditText) findViewById(R.id.sRollView);
                studentMarks = (EditText) findViewById(R.id.sMarksView);
                studentRating = (RatingBar) findViewById(R.id.ratingBar);

                name = studentName.getText().toString();
                rollno = studentRollNo.getText().toString();
                marks = studentMarks.getText().toString();
                rating = studentRating.getNumStars();

                boolean ans = isExternalStorageWritable();
                if(ans){
                    File f = getExternalStorageDir(getApplicationContext(),"studentsRec");
                    File record_file = new File(f, "studentData.txt");
                    try {
                        FileOutputStream fos1 = new FileOutputStream(record_file);
                        fos1.write(rollno.getBytes());
                        fos1.write(name.getBytes());
                        fos1.write(marks.getBytes());
                        fos1.write(rating);
                        fos1.close();
                        Toast.makeText(getApplicationContext(), "Data written to file!!", Toast.LENGTH_LONG).show();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                else if(!ans){
                    Toast.makeText(getApplicationContext(), "No writtable permission for external files. Change settings to save to file", Toast.LENGTH_LONG).show();
                }
            }
        });

        if(savedInstanceState!=null)
        {
            studentName = (EditText) findViewById(R.id.sNameView);
            studentRollNo = (EditText) findViewById(R.id.sRollView);
            studentMarks = (EditText) findViewById(R.id.sMarksView);
            studentRating = (RatingBar) findViewById(R.id.ratingBar);
            //If the activity is being restored, the saved state of activity is taken to recreated it.
            studentRollNo.setText(savedInstanceState.getString("rollfield"));
            studentName.setText(savedInstanceState.getString("name1field"));
            studentMarks.setText(savedInstanceState.getString("marksfield"));
            studentRating.setRating(savedInstanceState.getInt("ratingfield"));

        }
        else{
            studentName = (EditText) findViewById(R.id.sNameView);
            studentRollNo = (EditText) findViewById(R.id.sRollView);
            studentMarks = (EditText) findViewById(R.id.sMarksView);
            studentRating = (RatingBar) findViewById(R.id.ratingBar);
            studentRollNo.setText("");
            studentName.setText("");
            studentMarks.setText("");
            studentRating.setRating(0);

        }

    }

    protected void clearFieldsForNewEntry(){
        studentMarks.setText("");
        studentName.setText("");
        studentRollNo.setText("");
        studentRating.setRating(0);
    }

    protected void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("rollfield", studentRollNo.getText().toString());
        savedInstanceState.putString("name1field", studentName.getText().toString());
        savedInstanceState.putString("marksfield", studentMarks.getText().toString());
        savedInstanceState.putInt("ratingfield", studentRating.getNumStars());

        //To save current state details of the activity.
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public File getExternalStorageDir(Context context, String file_name) {

        File file = new File(context.getExternalFilesDir(
                Environment.DIRECTORY_DOCUMENTS), file_name);
        if (!file.mkdirs()) {
        }
        return file;
    }

}


