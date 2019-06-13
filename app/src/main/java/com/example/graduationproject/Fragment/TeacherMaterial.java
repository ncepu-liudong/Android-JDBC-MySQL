package com.example.graduationproject.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.graduationproject.R;
import com.example.graduationproject.StudentSignIn;
import com.example.graduationproject.TeacherLoginOut;
import com.example.graduationproject.tool.IntentTools;

public class TeacherMaterial extends Fragment {

    private Button signinout;
    private Button scoreout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teacher_material, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        init(view);
    }
    private void init(View view){

    }
}
