package com.sigran0.sendreceive.managers;


import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sigran0.sendreceive.activities.BaseActivity;
import com.sigran0.sendreceive.interfaces.FireabaseOAuthCallback;
import com.sigran0.sendreceive.interfaces.SigninCallback;

import java.util.Arrays;

public class UserManager {

    private static final String TAG = "good";

    public enum SigninType {
        None,
        Facebook,
        Twitter
    }

    public enum SigninState {
        None,
        Waiting,
        Signin
    }

    private SigninType signinType = SigninType.None;
    private SigninState signinState = SigninState.None;

    private static UserManager instance;
    private BinderManager binderManager = BinderManager.getInstance();
    private FirebaseUser user;
    private FirebaseAuth auth;
    private CallbackManager callbackManager;

    private UserManager(){
        auth = FirebaseAuth.getInstance();
    }

    public static UserManager getInstance(){

        if(instance == null)
            instance = new UserManager();

        return instance;
    }

    public boolean isSignin(){
        user = auth.getCurrentUser();
        return user != null;
    }

    public CallbackManager getCallbackManager(){
        return callbackManager;
    }

    public SigninState getSigninState(){
        return signinState;
    }

    public SigninType getSigninType(){
        return signinType;
    }

    public String getUID(){
        if(isSignin()) {
            return user.getUid();
        } else
            throw new IllegalStateException("User is not sigin yet");
    }

    public String getUsername(){
        if(isSignin()){
            return user.getDisplayName();
        } else
            throw new IllegalStateException("User is not signin yet.");
    }

    public String getUserEmail(){
        if(isSignin()){
            return user.getEmail();
        } else
            throw new IllegalStateException("User is not signin yet.");
    }

    public Uri getUserProfileImage(){
        if(isSignin()) {

            Uri imageUri = user.getPhotoUrl();

            return imageUri;
        } else
            throw new IllegalStateException("User is not signin yet.");
    }

    public void signOut(){
        auth.signOut();
    }

    public void signinWithSNS(final SigninType type, final BaseActivity activity, final SigninCallback callback){
        switch (type){
            case Facebook:
                signinWithFacebook(activity, callback);
                break;
            case Twitter:
                //signinWithTwitter(activity, callback);
                break;
            case None:
                throw new IllegalArgumentException("NONE has no method to signin");
        }
    }

    private void signinWithFacebook(final BaseActivity activity, final SigninCallback callback){

        setUserState(SigninType.Facebook, SigninState.Waiting);

        callbackManager = CallbackManager.Factory.create();

        LoginManager
                .getInstance()
                .registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        handleFacebookSession(activity, loginResult.getAccessToken(), new FireabaseOAuthCallback() {
                            @Override
                            public void success(Task<AuthResult> task) {
                                user = task.getResult().getUser();
                                setUserState(SigninState.Signin);

                                binderManager.startUpdate("signin", "success");

                                callback.success();
                            }

                            @Override
                            public void failed(Task<AuthResult> task) {
                                Log.e(TAG, "failed: fucing error", task.getException());
                                setUserState(SigninType.None, SigninState.None);
                                callback.failed();
                            }
                        });
                    }

                    @Override
                    public void onCancel() {
                        Log.d(TAG, String.format("signin cancel"));
                        setUserState(SigninType.None, SigninState.None);
                        callback.failed();
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d(TAG, String.format("signin error"));
                        setUserState(SigninType.None, SigninState.None);
                        callback.failed();
                    }
                });

        LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("public_profile", "user_friends", "email"));

    }

    private void handleFacebookSession(BaseActivity activity, AccessToken token, final FireabaseOAuthCallback callback){

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                            callback.success(task);
                        else
                            callback.failed(task);
                    }
                });
    }

    private void setUserState(SigninType type, SigninState state){
        signinType = type;
        signinState = state;
    }

    private void setUserState(SigninState state){
        signinState = state;
    }


}
