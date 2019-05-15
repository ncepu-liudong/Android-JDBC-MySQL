package com.example.graduationproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class StudentAddGroup extends AppCompatActivity {
    private TextView group_number;
    private Button join_group;
    protected static int c_id;
    protected static int u_id;
    protected static int e_id;
    protected static String j_state = "200";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_add_group);
        Intent intent = getIntent();
        String uid = intent.getStringExtra("u_id");
        String eid = intent.getStringExtra("e_id");
        u_id = Integer.valueOf(uid).intValue();
        e_id = Integer.valueOf(eid).intValue();
        init();
    }
    private void init(){
        group_number = findViewById(R.id.group_number);
        join_group = findViewById(R.id.join_group);
        join_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gnumber = group_number.getText().toString();
                c_id = Integer.valueOf(gnumber).intValue();
                JThread dt = new JThread();
                Thread thread = new Thread(dt);
                thread.start();
                for (;j_state.equals("200");){
                }
                Toast.makeText(StudentAddGroup.this, "加入分组成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(StudentAddGroup.this,StudentExperimentMember.class);
                startActivity(intent);
                StudentAddGroup.this.finish();
            }
        });
    }
}
class JThread implements Runnable {
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
    public void update(){
        Connection conn = getConnection();
        try {
            Statement st = conn.createStatement();
            String sql = "update choice set c_id="+StudentAddGroup.c_id+" where u_id="+StudentAddGroup.u_id+" and e_id="+StudentAddGroup.e_id+"";
            st.execute(sql);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void run(){
        update();
        StudentAddGroup.j_state="1";
    }

}