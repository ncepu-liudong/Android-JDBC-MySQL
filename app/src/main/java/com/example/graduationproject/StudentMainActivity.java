package com.example.graduationproject;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.graduationproject.Adapter.FragmentAdapter;
import com.example.graduationproject.Fragment.StudentExperiment;
import com.example.graduationproject.Fragment.StudentMaterial;
import com.example.graduationproject.Fragment.StudentMy;
import com.example.graduationproject.Fragment.StudentNotice;
import java.util.ArrayList;
import java.util.List;

public class StudentMainActivity extends AppCompatActivity implements
        BottomNavigationBar.OnTabSelectedListener, ViewPager.OnPageChangeListener  {

    private static boolean isExit = false;
    private ViewPager viewPager;
    private BottomNavigationBar bottomNavigationBar;
    int lastSelectePosition = 0;
    private List<Fragment> list = new ArrayList<>();
    public static String e_name;
    public static String e_teacher;
    public static String e_id1;
    public static String u_id;
    public static int e_id;
    public static int u_id1;
    public static String s_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main);
        Intent intent = getIntent();
        u_id = intent.getStringExtra("account");
        u_id1 = Integer.valueOf(u_id).intValue();
        e_name = intent.getStringExtra("e_name");
        e_teacher = intent.getStringExtra("e_teacher");
        e_id1 = intent.getStringExtra("e_id");
        e_id = Integer.valueOf(e_id1).intValue();
        Log.i("e_id",e_id1);
        s_name = intent.getStringExtra("s_name");
        InitViewPage();
        InitBottomNavigationBar();
    }
    private void InitViewPage(){
        viewPager = findViewById(R.id.view_fragment);
        list.add(new StudentExperiment());
        list.add(new StudentNotice());
        list.add(new StudentMaterial());
        list.add(new StudentMy());
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(),list);
        viewPager.addOnPageChangeListener(this);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);

    }
    //初始化BottomNavigationBar
    private void InitBottomNavigationBar(){
        bottomNavigationBar = findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar
                .setTabSelectedListener(this)
                .setMode(BottomNavigationBar.MODE_SHIFTING)
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE)
                .setActiveColor("#FFFFF0")
                .setInActiveColor("#696969")
                .setBarBackgroundColor("#6495ED");
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.shiyan1,"实验"))
                .addItem(new BottomNavigationItem(R.drawable.gonggao1,"公告"))
                .addItem(new BottomNavigationItem(R.drawable.ziliao,"资料"))
                .addItem(new BottomNavigationItem(R.drawable.yonghu1,"用户"))
                .setFirstSelectedPosition(lastSelectePosition)
                .initialise();
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK){
            exit();
            return false;
        }
        return super.onKeyDown(keyCode,event);
    }


    //再按一次退出程序
    private void exit(){
        if(!isExit){
            isExit=true;
            Toast.makeText(getApplicationContext(),"再按一次退出程序",Toast.LENGTH_SHORT).show();
            //利用handler延迟发送更改状态信息
            handler.sendEmptyMessageDelayed(0,2000);
        }
        else{
            finish();
            System.exit(0);
        }
    }

    @Override
    public void onTabSelected(int position) {
//        if(position==3){
//            Intent intent = getIntent();
//            String user_account = intent.getStringExtra("account");
//            Log.v("tag",user_account);
//            Bundle bundle = new Bundle();
//            bundle.putString("accounts",user_account);
//            myFragment = new MyFragment();
//            myFragment.setArguments(bundle);
//        }
        viewPager.setCurrentItem(position);
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    /*
     *
     * 以下三个方法是重写OnPageChangeListener中的方法
     */

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        bottomNavigationBar.selectTab(i);

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
