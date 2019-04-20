package com.example.tvcuo.sportf;

import android.content.Intent;
import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;

import com.facebook.CallbackManager;

import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONException;
import org.json.JSONObject;


import java.util.Arrays;

public class Dang_nhap extends AppCompatActivity {
ProfilePictureView imageProfile;
LoginButton buttonLogin;
Button buttonLogout;
TextView textNameProfile;
CallbackManager callbackManager;
String email, name, firstname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager=CallbackManager.Factory.create();
        setContentView(R.layout.activity_dang_nhap);
        imageProfile=findViewById(R.id.friendProfilePicture);
        buttonLogin=findViewById(R.id.login_button);
        buttonLogout=findViewById(R.id.logout_button);
        textNameProfile=findViewById(R.id.textView_ten_user);
        buttonLogout.setVisibility(View.INVISIBLE);
        textNameProfile.setText("");
        buttonLogin.setReadPermissions(Arrays.asList("public_profile","email"));
        setLogin_button();
        setLogout_button();

    }

    private void setLogout_button() {
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                buttonLogout.setVisibility(View.INVISIBLE);
                textNameProfile.setText("");
                imageProfile.setProfileId(null);
                SharedPreferencesManager.setEmail("");
                SharedPreferencesManager.setTenFB("");
                buttonLogin.setVisibility(View.VISIBLE);
                SharedPreferencesManager.setLogin(false);
            }
        });
    }

    private void setLogin_button() {
        buttonLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                result();
                buttonLogin.setVisibility(View.INVISIBLE);
                buttonLogout.setVisibility(View.VISIBLE);
                SharedPreferencesManager.setLogin(true);
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {

            }

        });

    }

    private void result() {
        GraphRequest request= GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                Log.d("JSON", response.getJSONObject().toString());
                try {
                    email=object.getString("email");
                    name=object.getString("name");
                    firstname=object.getString("first_name");
                    imageProfile.setProfileId(Profile.getCurrentProfile().getId());
                    SharedPreferencesManager.setEmail(email);
                    textNameProfile.setText(name);
                    SharedPreferencesManager.setTenFB(name);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "name,email,first_name");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }



    @Override
    protected void onStart() {
        if(SharedPreferencesManager.isLogin())
        {
            imageProfile.setProfileId(Profile.getCurrentProfile().getId());
            buttonLogin.setVisibility(View.INVISIBLE);
            textNameProfile.setText(Profile.getCurrentProfile().getName());
            buttonLogout.setVisibility(View.VISIBLE);
        }
        super.onStart();
    }
}
