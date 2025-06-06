package com.example.pract15;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        view.setOnCreateContextMenuListener((menu, v, menuInfo) -> {
            menu.add("Change Text").setOnMenuItemClickListener(item -> {
                TextView textView = view.findViewById(R.id.text_view);
                textView.setText("Text Changed!");
                return true;
            });
        });

        return view;
    }
}