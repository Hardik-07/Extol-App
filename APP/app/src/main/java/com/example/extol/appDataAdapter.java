package com.example.extol;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class appDataAdapter extends RecyclerView.Adapter<appDataAdapter.appDataViewHolder>{

    private Context mContext;
    private ArrayList<app_Items> mAppList;


    public appDataAdapter(Context context,ArrayList<app_Items> appList){
        mContext = context;
        mAppList = appList;
    }

    @NonNull
    @Override
    public appDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.custom_app_card,parent,false);
        return  new appDataViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull appDataViewHolder holder, final int position) {

        app_Items  currentItem =  mAppList.get(position);

        String iconURL =  currentItem.get_mIcon_url();
        String appName =  currentItem.get_mAppName();
        final String app_url = currentItem.get_mAppUrl();
        String score = currentItem.get_mScore();

        holder.mApp_name.setText(appName);
        holder.mApp_score.setText("SCORE : "+score);
        Picasso.get().load(iconURL).fit().centerInside().into(holder.mApp_icon);
        holder.mAppData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,Uri.parse(app_url));
                mContext.startActivity(browserIntent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return mAppList.size();
    }


    public class  appDataViewHolder extends  RecyclerView.ViewHolder{

        public ImageView mApp_icon;
        public TextView mApp_name,mApp_score;
        public ConstraintLayout mAppData;

        public appDataViewHolder(@NonNull View itemView) {
            super(itemView);

            mApp_icon = itemView.findViewById(R.id.app_icon_view);
            mApp_name = itemView.findViewById(R.id.app_name_view);
            mAppData =  itemView.findViewById(R.id.app_data_view);
            mApp_score = itemView.findViewById(R.id.app_score_view);
        }
    }


}
