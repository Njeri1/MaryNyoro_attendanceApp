package com.example.attendance;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper attendanceDB;
    Button btnAddData, btnViewData, btnUpdateData, btnDeleteData;
    EditText etName,etEmail,etStudent_id,etPassword,etGender,etSession,etDepartment, etID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        attendanceDB = new DatabaseHelper(this);
        etName = (EditText) findViewById(R.id.student_name);
        etStudent_id = (EditText) findViewById(R.id.student_id);
        etEmail = (EditText) findViewById(R.id.email);
        etPassword = (EditText) findViewById(R.id.password);
        etGender = (EditText) findViewById(R.id.gender);
        etDepartment = (EditText) findViewById(R.id.department);
        etSession = (EditText) findViewById(R.id.session);
        etID = (EditText) findViewById(R.id.etID);
        btnAddData = (Button) findViewById(R.id.add);
        btnViewData = (Button) findViewById(R.id.read);
        btnUpdateData = (Button) findViewById(R.id.update);
        btnDeleteData = (Button) findViewById(R.id.delete);
        AddData();
        ViewData();
        UpdateData();
        DeleteData();
        etStudent_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity();
            }
        });

    }
    public void openNewActivity(){
        Intent intent = new Intent(this, barcodeScanner.class);
        startActivity(intent);
    }

    public void AddData(){
        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String student_id = etStudent_id.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String gender = etGender.getText().toString();
                String department = etDepartment.getText().toString();
                String session = etSession.getText().toString();

                boolean insertData = attendanceDB.addData(name,student_id,email,password,gender,department,session);

                if (insertData == true){
                    Toast.makeText(MainActivity.this, "Data successfully inserted!!!",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(MainActivity.this, "Something went Wrong :(",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void ViewData(){
        btnViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor data = attendanceDB.showData();

                if (data.getCount() == 0){
                    display("Error!", "No Data Found!!");

                }
                StringBuffer buffer = new StringBuffer();
                while (data.moveToNext()){
                    buffer.append("ID: " + data.getString(0) + "\n");
                    buffer.append("Name: " + data.getString(1) + "\n");
                    buffer.append("Student_id: " + data.getString(2) + "\n");
                    buffer.append("Email: " + data.getString(3) + "\n");
                    buffer.append("Password: " + data.getString(4) + "\n");
                    buffer.append("Gender: " + data.getString(5) + "\n");
                    buffer.append("Department: " + data.getString(6) + "\n");
                    buffer.append("Session: " + data.getString(7) + "\n");

                    display("All Stored Data:", buffer.toString());

                }
            }
        });
    }

    public void display(String title, String message){
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void UpdateData(){
        btnUpdateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = etID.getText().toString().length();
                if (temp > 0){
                    boolean update = attendanceDB.updateData(etID.getText().toString(),etName.getText().toString(),etStudent_id.getText().toString()
                            ,etPassword.getText().toString(),etEmail.getText().toString(),
                            etGender.getText().toString(),etDepartment.getText().toString(),etSession.getText().toString());
                    if (update == true){
                        Toast.makeText(MainActivity.this, "Successfully Updated Data!!",Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(MainActivity.this, "Somethings Not Right!! :(",Toast.LENGTH_LONG).show();
                    }

                }else {
                    Toast.makeText(MainActivity.this, "You have to enter an ID to update :( ",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void DeleteData(){
        btnDeleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp =etID.getText().toString().length();
                if (temp > 0){
                    Integer deleteRow = attendanceDB.deleteData(etID.getText().toString());
                    if (deleteRow > 0){
                        Toast.makeText(MainActivity.this, "Successfully Deleted the Data!! ",Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(MainActivity.this, "Something went Wrong :(",Toast.LENGTH_LONG).show();
                    }

                }else {
                    Toast.makeText(MainActivity.this, "You have to enter an ID to Delete :(",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
