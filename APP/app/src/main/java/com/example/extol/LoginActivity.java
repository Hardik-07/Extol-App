package com.example.extol;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.squareup.picasso.Picasso;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener{
    public String name,email;
    public LinearLayout prof_sect;
    public Button logout;
    public SignInButton signin;
    public TextView Name,Email;
    public ImageView prof_pic;
    private GoogleApiClient googleApiClient;
    private static final int REQ_CODE = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        prof_sect = (LinearLayout) findViewById(R.id.profile_section);
        logout = (Button) findViewById(R.id.logout);
        signin = (SignInButton) findViewById(R.id.btn_login);
        Name = (TextView) findViewById(R.id.name);
        Email = (TextView) findViewById(R.id.email);
        prof_pic = (ImageView) findViewById(R.id.profile_pic);
        signin.setOnClickListener(this);
        logout.setOnClickListener(this);
        prof_sect.setVisibility(View.GONE);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_login:
                signin();
                break;
            case R.id.logout:
                signout();
                break;
        }
    }
    private void signin(){
        Toast.makeText(this,"Sign in",Toast.LENGTH_LONG) ;
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent,REQ_CODE);

    }
    private void signout(){
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                updateUI(false);
            }
        });
    }
    public void handleResult(GoogleSignInResult result){
        if(result.isSuccess()){
            GoogleSignInAccount account = result.getSignInAccount();
            String name = account.getDisplayName();
            String email = account.getEmail();
            String img;
            if(account.getPhotoUrl()==null){
                img = "https://pluspng.com/img-png/user-png-icon-male-user-icon-512.png";
            }
            else{
                img = account.getPhotoUrl().toString();
            }
            Picasso.get().load(img).fit().centerInside().into(prof_pic);
            //img = account.getPhotoUrl().toString();
            Toast.makeText(this,""+img , Toast.LENGTH_LONG).show();
            Name.setText(name);
            Email.setText(email);
            //Glide.with(this).load(img).into(prof_pic);
            updateUI(true);
        }
        else{
            updateUI(false);
        }

    }
    public void updateUI(boolean isLogin){
        if(isLogin){
            Intent i = new Intent(LoginActivity.this,MainActivity.class);
            i.putExtra("name",name);
            i.putExtra("email",email);
            startActivity(i);
            finish();
            Toast.makeText(this,"Login Successfully" , Toast.LENGTH_LONG).show();
            //prof_sect.setVisibility(View.VISIBLE);
            //signin.setVisibility(View.GONE);
        }
        else{
            Intent i = new Intent(this,LoginActivity.class);
            startActivity(i);
            Toast.makeText(this,"Login Unsuccessfully", Toast.LENGTH_LONG).show();
            prof_sect.setVisibility(View.GONE);
            signin.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ_CODE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);
        }
    }
}
