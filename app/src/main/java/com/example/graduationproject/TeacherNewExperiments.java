package com.example.graduationproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TeacherNewExperiments extends AppCompatActivity {
    protected static String u_id;
    protected static String e_name;
    protected static int e_number;
    protected static int e_group = 0;
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
        experiment_g_number.setFocusable(false);
        experiment_g_number.setFocusableInTouchMode(false);
        experiment_g_number.setInputType(InputType.TYPE_NULL);

        experiment_group.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    experiment_g_number.setFocusable(true);
                    experiment_g_number.setFocusableInTouchMode(true);
                    experiment_g_number.setInputType(InputType.TYPE_CLASS_TEXT);
                    e_group = 1;
                }else{
                    experiment_g_number.setFocusable(false);
                    experiment_g_number.setFocusableInTouchMode(false);
                    experiment_g_number.setInputType(InputType.TYPE_NULL);
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
                EBThread dt = new EBThread();
                Thread thread = new Thread(dt);
                thread.start();
                for (;e_state.equals("200");){
                }
                Toast.makeText(TeacherNewExperiments.this, "创建新实验成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TeacherNewExperiments.this,TeacherLogon.class);
                startActivity(intent);
                TeacherNewExperiments.this.finish();
            }
        });
    }
}
class EBThread implements Runnable {
    private static String driver = DateSet.getDriver();
    private static String url = DateSet.getUrl();
    private static String user = DateSet.getUser();
    private static String password = DateSet.getPassword();

    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return con;
    }
    public void query(){
        Connection conn = getConnection();
        try {
            Statement st = conn.createStatement();
            String sql ="insert into experiments (e_name,u_id,e_number,e_group) values ('"+TeacherNewExperiments.e_name+"',"+TeacherNewExperiments.u_id+"," +
                    ""+TeacherNewExperiments.e_number+","+TeacherNewExperiments.e_group+")";
            st.execute(sql);
            conn.close();
            st.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void run(){
        query();
        TeacherNewExperiments.e_state="1";
    }

}
