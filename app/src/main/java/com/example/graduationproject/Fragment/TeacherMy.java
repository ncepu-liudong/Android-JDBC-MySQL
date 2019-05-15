package com.example.graduationproject.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.graduationproject.Adapter.MyItemAdapter;
import com.example.graduationproject.LogonActivity;
import com.example.graduationproject.R;
import com.example.graduationproject.TeacherMainActivity;
import com.example.graduationproject.TeacherUserInformation;
import com.example.graduationproject.tool.IntentTools;

public class TeacherMy extends Fragment {

    private TextView name;
    private ListView listView;
    private ImageView imageView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_teacher_my, container, false);
        final String user_name = TeacherMainActivity.e_teacher;
        name = view.findViewById(R.id.account);
        name.setText(user_name);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        init(view);
    }
    private void Listener(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        IntentTools.getInstance().intent(getContext(), TeacherUserInformation.class,null);
                        break;
                    case 1:
                        getActivity().finish();
                        break;
               }
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentTools.getInstance().intent(getContext(), LogonActivity.class,null);
            }
        });
    }
    private void init(View v){
        imageView = v.findViewById(R.id.item_logo1);
        listView = v.findViewById(R.id.list_item);
        MyItemAdapter myItemAdapter = new MyItemAdapter(v.getContext());
        listView.setAdapter(myItemAdapter);
        Listener();
    }
}
