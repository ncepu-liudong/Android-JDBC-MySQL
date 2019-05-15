package com.example.graduationproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StudentNewExperiment extends AppCompatActivity {
    private EditText j_experiment;
    private Button e_join;
    protected static int u_id;
    protected static int e_id;
    protected static String e_state = "200";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_new_experiment);
        Intent intent = getIntent();
        String uid = intent.getStringExtra("account");
        u_id = Integer.valueOf(uid).intValue();
        init();
    }
    private void init(){
        j_experiment = findViewById(R.id.j_experiment);
        e_join = findViewById(R.id.e_join);
        e_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eid = j_experiment.getText().toString();
                e_id = Integer.valueOf(eid).intValue();

                IDBThread dt = new IDBThread();
                Thread thread = new Thread(dt);
                thread.start();
                for (;e_state.equals("200");){
                }
                if (e_state.equals("0")){
                    Toast.makeText(StudentNewExperiment.this, "请核对试验编号重新输入", Toast.LENGTH_SHORT).show();
                    j_experiment.setText("");
                    e_state="200";
                }else if(e_state.equals("1")){
                    Toast.makeText(StudentNewExperiment.this, "加入实验成功!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(StudentNewExperiment.this,StudentLogon.class);
                    startActivity(intent);
                    StudentNewExperiment.this.finish();
                }


            }
        });
    }
}
class IDBThread implements Runnable {
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
    public void inserts(){
        Connection conn = getConnection();
        try{
            Statement st = conn.createStatement();
            String sql = "select * from experiments where e_id="+StudentNewExperiment.e_id+"";
            ResultSet rt = st.executeQuery(sql);
            if (rt.next()==false){
                StudentNewExperiment.e_state = "0";
            }else {
                String egroup = rt.getString("e_group");
                int e_group = Integer.valueOf(egroup).intValue();
                sql = "insert into choice ( u_id,e_id,e_group) values ("+StudentNewExperiment.u_id+","+StudentNewExperiment.e_id+","+e_group+")";
                st.execute(sql);
                StudentNewExperiment.e_state = "1";
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
        inserts();
    }

}
