package com.example.graduationproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class TeacherSignIn extends AppCompatActivity {
    private TextView e_name;
    private TextView e_id;
    private TextView e_teacher;
    private ListView listView;
    private SimpleAdapter adapter;
    protected static String s_state = "200";

    protected static List<Map<String, ?>> list = new ArrayList<Map<String, ?>>();
    protected static int[] to = new int[]{R.id.item_text1,R.id.item_text2};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_sign_in);
        init();
    }
    private void init(){
        e_id = findViewById(R.id.e_id1);
        e_name = findViewById(R.id.e_name);
        e_teacher = findViewById(R.id.e_teacher);
        listView = findViewById(R.id.signlist);
        String eid = TeacherMainActivity.e_id1;
        String ename = TeacherMainActivity.e_name;
        String eteacher = TeacherMainActivity.e_teacher;
        Log.i("eid",eid);
        e_id.setText(eid);
        e_name.setText(ename);
        e_teacher.setText(eteacher);
        GThread dt = new GThread();
        Thread thread = new Thread(dt);
        thread.start();
        for (;s_state.equals("200");){
        }
        adapter = new SimpleAdapter(this,list,R.layout.teacher_sign_list_item,new String[]{"name","signin"},to);
        listView.setAdapter(adapter);
    }
}
class GThread implements Runnable {
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
            String sql = "select u_id from choice where e_id= "+TeacherMainActivity.e_id+"";
            String sql2;
            ResultSet rt = st.executeQuery(sql);
            ResultSet rtt;
            TeacherSignIn.list.clear();
            while (rt.next()){
                Map<String,String> keyvalue = new HashMap<String,String>();
                String uid = rt.getString("u_id");
                int u_id = Integer.valueOf(uid).intValue();
                sql2 = "select u_name from user where u_id="+u_id+"";
                rtt = stt.executeQuery(sql2);
                rtt.next();
                String u_name = rtt.getString("u_name");
                keyvalue.put("name",u_name);
                sql2 = "select * from signin where u_id="+u_id+" and e_id="+TeacherMainActivity.e_id+"";
                rtt = stt.executeQuery(sql2);
                if(rtt.next()==false){
                    keyvalue.put("signin","未签到");
                }else {
                    keyvalue.put("signin","已签到");
                }
                TeacherSignIn.list.add(keyvalue);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void run(){
        query();
        TeacherSignIn.s_state="1";
    }

}
