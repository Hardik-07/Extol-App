package com.example.extol;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    RecyclerView recyclerApp;
    appDataAdapter mDataAdapter;
    ArrayList<app_Items> mAppList;
    RequestQueue mRequestQueue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home,container,false);
        recyclerApp = view.findViewById(R.id.app_recycler_view);
        recyclerApp.setHasFixedSize(true);
        recyclerApp.setLayoutManager(new LinearLayoutManager(getContext()));

        mAppList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(getActivity());

        getJsonData();

        return inflater.inflate(R.layout.fragment_home,container,false);

    }

    private void getJsonData() {

        String url = "https://hardik007.pythonanywhere.com/games";
        JsonArrayRequest arrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // JsonObject jsonObject  =  response.getJSONObject(i);
                        int count=0;
                        for(int i=0 ;i<response.length();i++){
                            try {
                                JSONObject appObject = response.getJSONObject(i);

                                String appName = appObject.getString("name");
                                String iconURL = appObject.getString("icon");
                                String appScore =  appObject.getString("score");
                                String appUrl =  appObject.getString("url");
                                count = i;
                                mAppList.add(new app_Items(iconURL,appName,appUrl,appScore));
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        mDataAdapter = new appDataAdapter(getContext(),mAppList);
                        recyclerApp.setAdapter(mDataAdapter);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.d("error","timeout");
                    }
                }

        );
        arrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                80000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        mRequestQueue.add(arrayRequest);

    }/*
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.Planets, android.R.layout.simple_list_item_1);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
    }*/

}
