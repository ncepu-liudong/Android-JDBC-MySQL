package com.example.graduationproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentLogon extends AppCompatActivity {

    protected static String u_id;
    protected static String teacher;
    protected static String e_state="200";
    protected static String s_name;
    private ListView listView;
    private ImageButton new_experiment;
    private SimpleAdapter adapter;

    protected static List<Map<String, ?>> list = new ArrayList<Map<String, ?>>();
    protected static int[] to = new int[]{R.id.e_id,R.id.e_name,R.id.e_number,R.id.e_teacher};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_logon);
        Intent intent = getIntent();
        u_id = intent.getStringExtra("account");
        Log.i("u_id",u_id);
        listView = findViewById(R.id.experiment_item);
        new_experiment = findViewById(R.id.new_experiment);
        FThread dt = new FThread();
        Thread thread = new Thread(dt);
        thread.start();
        for (;e_state.equals("200");){
        }
        adapter = new SimpleAdapter(this,list,R.layout.teacher_experiments_list_item,new String[]{"identity","name","number","teacher"},to);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView ename = (TextView)view.findViewById(R.id.e_name);
                TextView eteacher = (TextView)view.findViewById(R.id.e_teacher);
                TextView enumber = (TextView)view.findViewById(R.id.e_number);
                TextView eid = (TextView)view.findViewById(R.id.e_id);
                String name = ename.getText().toString();
                teacher = eteacher.getText().toString();
                String number = enumber.getText().toString();
                String idemtity = eid.getText().toString();
                Intent intent = new Intent(getApplicationContext(),StudentMainActivity.class);
                intent.putExtra("e_name",name);
                intent.putExtra("e_id",idemtity);
                intent.putExtra("e_teacher",teacher);
                intent.putExtra("account",u_id);
                intent.putExtra("s_name",s_name);
                startActivity(intent);
            }
        });
        new_experiment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentLogon.this,StudentNewExperiment.class);
                intent.putExtra("account",u_id);
                startActivity(intent);
            }
        });
    }
}
class FThread implements Runnable {
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
            Statement stt = conn.createStatement();
            String sql = "select * from choice where u_id= "+StudentLogon.u_id+"";
            String sql2;
            ResultSet rt = st.executeQuery(sql);
            ResultSet rtt;
            while (rt.next()){
                Map<String,String> keyvalue = new HashMap<String,String>();
                String eid = rt.getString("e_id");
                int e_id = Integer.valueOf(eid).intValue();
                keyvalue.put("identity",eid);
                sql2 = "select * from experiments where e_id="+e_id+"";
                rtt = stt.executeQuery(sql2);
                rtt.next();
                String e_name = rtt.getString("e_name");
                String e_number = rtt.getString("e_number");
                String uid = rtt.getString("u_id");
                int u_id = Integer.valueOf(uid).intValue();
                keyvalue.put("name",e_name);
                keyvalue.put("number",e_number);
                sql2 = "select * from user where u_id="+u_id+"";
                rtt = stt.executeQuery(sql2);
                rtt.next();
                String e_teacher = rtt.getString("u_name");
                keyvalue.put("teacher",e_teacher);
                StudentLogon.list.add(keyvalue);

            }
            sql2 = "select * from user where u_id="+StudentLogon.u_id+"";
            rtt = stt.executeQuery(sql2);
            rtt.next();
            StudentLogon.s_name = rtt.getString("u_name");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void run(){
        query();
        StudentLogon.e_state="1";
    }

}
