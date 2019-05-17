package com.example.graduationproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Admin_List_Activity extends AppCompatActivity implements View.OnClickListener{

    private Button teacherlist;
    private Button studentlist;
    private Button finduser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__list_);
        init();
    }
    private void init(){
        teacherlist = findViewById(R.id.a_teacherlist);
        studentlist = findViewById(R.id.a_studentlist);
        finduser = findViewById(R.id.a_userfind);

        teacherlist.setOnClickListener(this);
        studentlist.setOnClickListener(this);
        finduser.setOnClickListener(this);
    }
    public void onClick(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.a_studentlist:
                intent = new Intent(Admin_List_Activity.this,AdminStudentList.class);
                startActivity(intent);
                Admin_List_Activity.this.finish();
                break;
            case R.id.a_teacherlist:
                intent = new Intent(Admin_List_Activity.this,AdminTeacherList.class);
                startActivity(intent);
                Admin_List_Activity.this.finish();
                break;
            case R.id.a_userfind:
                intent = new Intent(Admin_List_Activity.this,AdminFindUser.class);
                startActivity(intent);
                Admin_List_Activity.this.finish();
                break;
        }
    }
}
