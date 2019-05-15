package com.example.graduationproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

public class TeacherExperimentCheck extends AppCompatActivity {

    private TextView e_name;
    private ListView listView;
    private SimpleAdapter adapter;
    protected static String c_state = "200";

    protected static List<Map<String, ?>> list = new ArrayList<Map<String, ?>>();
    protected static int[] to = new int[]{R.id.g_number,R.id.s_name,R.id.s_id};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_experiment_check);
        init();
    }
    private void init(){
        e_name = findViewById(R.id.e_name);
        listView = findViewById(R.id.signlist);
        e_name.setText(TeacherMainActivity.e_name);
        KThread dt = new KThread();
        Thread thread = new Thread(dt);
        thread.start();
        for (;c_state.equals("200");){
        }
        adapter = new SimpleAdapter(this,list,R.layout.student_item,new String[]{"group","name","id"},to);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView gnumber = (TextView)view.findViewById(R.id.g_number);
                TextView sname = (TextView)view.findViewById(R.id.s_name);
                TextView sid = (TextView)view.findViewById(R.id.s_id);
                String g_number = gnumber.getText().toString();
                String s_name = sname.getText().toString();
                String u_id = sid.getText().toString();
                String e_id = TeacherMainActivity.e_id1;
                Intent intent = new Intent(TeacherExperimentCheck.this,TeacherExperimentSetScore.class);
                intent.putExtra("u_id",u_id);
                intent.putExtra("e_id",e_id);
                intent.putExtra("u_name",s_name);
                intent.putExtra("c_id",g_number);
                startActivity(intent);
                TeacherExperimentCheck.this.finish();
            }
        });
    }
}
class KThread implements Runnable {
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
            String sql = "select * from choice where e_id= "+TeacherMainActivity.e_id+" order by c_id asc";
            String sql2;
            ResultSet rt = st.executeQuery(sql);
            ResultSet rtt;
            TeacherExperimentCheck.list.clear();
            while (rt.next()){
                Map<String,String> keyvalue = new HashMap<String,String>();
                String uid = rt.getString("u_id");
                String gr_id = rt.getString("c_id");
                String g_id;
                if (gr_id==null){
                    g_id = "无";
                }else {
                    g_id = gr_id;
                }
                int u_id = Integer.valueOf(uid).intValue();
                sql2 = "select u_name from user where u_id="+u_id+"";
                rtt = stt.executeQuery(sql2);
                rtt.next();
                String u_name = rtt.getString("u_name");
                keyvalue.put("name",u_name);
                keyvalue.put("group",g_id);
                keyvalue.put("id",uid);
                TeacherExperimentCheck.list.add(keyvalue);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void run(){
        query();
        TeacherExperimentCheck.c_state="1";
    }
}
