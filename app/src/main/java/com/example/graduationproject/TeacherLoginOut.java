package com.example.graduationproject;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.graduationproject.Bean.SignInBean;
import com.example.graduationproject.Utils.SignInUtils;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TeacherLoginOut extends AppCompatActivity{
    private TextView title;
    private AlertDialog mDialog;

    private static String driver = DateSet.getDriver();
    private static String url = DateSet.getUrl();
    private static String user = DateSet.getUser();
    private static String password = DateSet.getPassword();
    private static String state = "200";
    private String filePath = Environment.getExternalStorageDirectory() + "/" + TeacherMainActivity.e_name+
            TeacherMainActivity.e_id1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login_out);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        title = findViewById(R.id.title);

        exportExcel(this);
        for(;state.equals("200");){

        }
        title.setText("导出成绩单成功！");
    }
    private void exportExcel(Context context) {
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String excelFileName = "/成绩单.xls";
        String[] title = {"学号", "实验号" , "实验成绩"};
        String sheetName = "成绩单";
        List<SignInBean> demoBeanList = new ArrayList<>();

        Connection conn = getConnection();
        try{
            Statement st = conn.createStatement();
            String sql = "select * from choice where e_id = "+TeacherMainActivity.e_id+"";
            ResultSet rt = st.executeQuery(sql);
            while (rt.next()){
                SignInBean s = new SignInBean(rt.getString("u_id"),rt.getString("e_id"),rt.getString("score"));
                demoBeanList.add(s);
            }
            state = "1";
        }catch (Exception e){
            e.printStackTrace();
        }
        filePath = filePath + excelFileName;


        SignInUtils.initExcel(filePath, sheetName, title);
        SignInUtils.writeObjListToExcel(demoBeanList, filePath, context);

    }
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
}
