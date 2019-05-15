package com.example.graduationproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TeacherExperimentSetScore extends AppCompatActivity {
    private TextView e_name;
    private TextView u_id;
    private TextView u_name;
    private EditText score;
    private Button sure;
    private String ename;
    private String uid;
    private String uname;
    private String cid;
    private String record;
    private String eid;
    protected static int u_id1;
    protected static int e_id1;
    protected static double record1;
    protected static String s_state = "200";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_experiment_set_score);
        Intent intent = getIntent();
        ename = intent.getStringExtra("u_name");
        uid = intent.getStringExtra("u_id");
        uname = intent.getStringExtra("u_name");
        cid = intent.getStringExtra("c_id");
        eid = intent.getStringExtra("e_id");
        init();
    }
    private void init(){
        e_name = findViewById(R.id.e_name);
        u_id = findViewById(R.id.u_id);
        u_name = findViewById(R.id.u_name);
        score = findViewById(R.id.score);
        sure = findViewById(R.id.sure);
        e_name.setText(ename);
        u_id.setText(uid);
        u_name.setText(uname);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                record = score.getText().toString();
                record1 = Integer.valueOf(record).doubleValue();
                u_id1 = Integer.valueOf(uid).intValue();
                e_id1 = Integer.valueOf(eid).intValue();
                LThread dt = new LThread();
                Thread thread = new Thread(dt);
                thread.start();
                for (;s_state.equals("200");){
                }
                Intent intent = new Intent(TeacherExperimentSetScore.this,TeacherExperimentCheck.class);
                startActivity(intent);
                TeacherExperimentSetScore.this.finish();
            }
        });
    }
}
class LThread implements Runnable {
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
            String sql = "update choice set record="+TeacherExperimentSetScore.record1+" where u_id="+TeacherExperimentSetScore.u_id1+" and e_id="+TeacherExperimentSetScore.e_id1+"";
            st.execute(sql);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void run(){
        update();
        TeacherExperimentSetScore.s_state="1";
    }

}