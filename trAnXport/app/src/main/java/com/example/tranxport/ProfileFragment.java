package com.example.tranxport;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputEditText;

public class ProfileFragment extends Fragment {


    SharedPreferences sp;


    TextInputEditText Tame;
    TextInputEditText Temail;
    TextInputEditText Tmobile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

//    private void setup() {
//        sp = getActivity().getSharedPreferences("MySp", Context.MODE_PRIVATE);
//        sp.getString("username", null);
//        sp.getString("email", null);
//        sp.getString("password", null);
//        sp.getString("mobile", null);
//    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sp = getActivity().getSharedPreferences("MySp", Context.MODE_PRIVATE);

        Tame = getActivity().findViewById(R.id.TIUsername);
        Temail = getActivity().findViewById(R.id.TI_email);
        Tmobile = getActivity().findViewById(R.id.TI_mobile);




        String username = sp.getString("username", "a");
        String email = sp.getString("email", null);
        String mobile=  sp.getString("mobile", null);
        Tame.setText(username);
        Temail.setText(email);
        Tmobile.setText(mobile);
    }
}