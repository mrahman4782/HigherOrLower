package com.example.finalproject;

import android.os.Bundle;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class endFragment extends Fragment {

    String inpText = "";

    public endFragment(String text){
        this.inpText = text;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_end, container, false);
        TextView scoreText = (TextView) v.findViewById(R.id.insText2);

        scoreText.setText(inpText);

        // Inflate the layout for this fragment
        return v;
    }
}