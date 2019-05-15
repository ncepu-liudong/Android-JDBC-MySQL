package com.example.graduationproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TeacherUserInformation extends AppCompatActivity {
    private TextView in_account;
    private TextView in_name;
    private TextView in_identity;
    private TextView in_accounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_user_information);

        String i_account = "";
        String i_name = "";
        String i_identity = "";
        in_account = findViewById(R.id.i_account);
        in_accounts = findViewById(R.id.i_account1);
        in_name = findViewById(R.id.i_name);
        in_identity = findViewById(R.id.i_identity);

        in_account.setText(TeacherMainActivity.e_teacher);
        in_accounts.setText(TeacherMainActivity.u_id);
        in_identity.setText("教师");
        in_name.setText(TeacherMainActivity.e_teacher);
    }
}
