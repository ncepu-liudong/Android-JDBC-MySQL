package com.example.graduationproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class TeacherNewExperiments extends AppCompatActivity {
    protected static String u_id;
    protected static String e_name;
    protected static int e_number;
    protected static int e_group = 0;
    protected static int e_g_n;
    protected static String e_state = "200";
    private Button e_create;
    private EditText experiment_name;
    private EditText experiment_number;
    private Switch experiment_group;
    private EditText experiment_g_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_new_experiments);
        Intent intent = getIntent();
        u_id = intent.getStringExtra("account");
        init();
    }
    private void init(){
        e_create = findViewById(R.id.e_create);
        experiment_name = findViewById(R.id.experiment_name);
        experiment_number = findViewById(R.id.experiment_number);
        experiment_group = findViewById(R.id.experiment_group);
        experiment_g_number = findViewById(R.id.experiment_g_number);

        experiment_group.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    experiment_g_number.setFocusable(true);
                    experiment_g_number.setFocusableInTouchMode(true);
                    e_group = 1;
                }else{
                    experiment_g_number.setFocusable(false);
                    experiment_g_number.setFocusableInTouchMode(false);
                    e_group = 0;
                }
            }
        });
        e_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                e_name = experiment_name.getText().toString();
                String n = experiment_number.getText().toString();
                e_number = Integer.valueOf(n).intValue();
                String n1 = experiment_g_number.getText().toString();
                e_g_n = Integer.valueOf(n1).intValue();

                //打开新的线程操作
                //判断e_state
                //显示操作成功
                Toast.makeText(TeacherNewExperiments.this, "创建新实验成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TeacherNewExperiments.this,TeacherLogon.class);
                startActivity(intent);
            }
        });
    }
}
