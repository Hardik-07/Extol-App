package com.example.extol;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile,container,false);
        Intent i = new Intent(getActivity(),LoginActivity.class);
        Bundle name = i.getExtras();
        String username = name.getString("name");
        String email = name.getString("email");
        TextView Username = (TextView) v.findViewById(R.id.username);
        TextView Email = (TextView) v.findViewById(R.id.email);
        Username.setText(username);
        Email.setText(email);
        return inflater.inflate(R.layout.fragment_profile,container,false);
    }

}