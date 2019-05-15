package com.example.graduationproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StudentExperimentScore extends AppCompatActivity {
    private TextView name;
    private TextView id;
    private TextView experiment;
    private TextView group;
    private TextView score;
    protected static String s_state="200";
    protected static String u_score;
    protected static String c_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_experiment_score);
        init();
    }
    private void init(){
        name = findViewById(R.id.name);
        id = findViewById(R.id.id);
        experiment = findViewById(R.id.experiment);
        group = findViewById(R.id.group);
        score = findViewById(R.id.score);
        EDBThread dt = new EDBThread();
        Thread thread = new Thread(dt);
        thread.start();
        for (;s_state.equals("200");){
        }
        name.setText(StudentMainActivity.s_name);
        id.setText(StudentMainActivity.u_id);
        group.setText(c_id);
        experiment.setText(StudentMainActivity.e_name);
        score.setText(u_score);
    }
}
class EDBThread implements Runnable {
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
    public void querys(){
        Connection conn = getConnection();
        try{
            Statement st = conn.createStatement();
            String sql = "select * from choice where u_id="+StudentMainActivity.u_id1+" and e_id="+StudentMainActivity.e_id+"";
            ResultSet rt = st.executeQuery(sql);
            rt.next();
            if(rt.getString("c_id")==null){
                StudentExperimentScore.c_id="无";
            }else{
                StudentExperimentScore.c_id=rt.getString("c_id");
            }
            if (rt.getString("record")==null){
                StudentExperimentScore.u_score="无";
            }else{
                StudentExperimentScore.u_score=rt.getString("record");
            }
            conn.close();
            st.close();
            rt.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void run(){
        querys();
        StudentExperimentScore.s_state="1";
    }

}
