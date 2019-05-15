package com.example.graduationproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class TeacherLogon extends AppCompatActivity {

    protected static String u_id;
    protected static String teacher;
    protected static String e_state="200";
    private ListView listView;
    private ImageButton new_experiment;
    private SimpleAdapter adapter;

    protected static List<Map<String, ?>> list = new ArrayList<Map<String, ?>>();
    protected static int[] to = new int[]{R.id.e_name,R.id.e_teacher,R.id.e_number,R.id.e_id};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_logon);
        Intent intent = getIntent();
        u_id = intent.getStringExtra("account");
        listView = findViewById(R.id.experiment_item);
        new_experiment = findViewById(R.id.new_experiment);
        EThread dt = new EThread();
        Thread thread = new Thread(dt);
        thread.start();
        for (;e_state.equals("200");){
        }
        adapter = new SimpleAdapter(this,list,R.layout.teacher_experiments_list_item,new String[]{"name","teacher","number","identity"},to);
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
                Intent intent = new Intent(getApplicationContext(),TeacherMainActivity.class);
                intent.putExtra("e_name",name);
                intent.putExtra("e_id",idemtity);
                intent.putExtra("e_teacher",teacher);
                intent.putExtra("account",u_id);
                startActivity(intent);
            }
        });
        new_experiment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherLogon.this,TeacherNewExperiments.class);
                intent.putExtra("account",u_id);
                startActivity(intent);
            }
        });
    }
}
class EThread implements Runnable {
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
            String sql ="select * from user where u_id="+TeacherLogon.u_id+"";
            ResultSet rt = st.executeQuery(sql);
            rt.next();
            String name = rt.getString("u_name");
            String sql2 = "select * from experiments where u_id="+TeacherLogon.u_id+"";
            ResultSet rt2 = st.executeQuery(sql2);
            TeacherLogon.list.clear();
            while (rt2.next()){
                Map<String,String> keyvalue = new HashMap<String,String>();
                keyvalue.put("name",rt2.getString("e_name"));
                keyvalue.put("teacher",name);
                keyvalue.put("number",rt2.getString("e_number"));
                keyvalue.put("identity",rt2.getString("e_id"));
                TeacherLogon.list.add(keyvalue);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void run(){
        query();
        TeacherLogon.e_state="1";
    }

}
