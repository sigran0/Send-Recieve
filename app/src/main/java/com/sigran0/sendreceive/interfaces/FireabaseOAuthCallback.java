package com.sigran0.sendreceive.interfaces;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public interface FireabaseOAuthCallback {
    public void success(Task<AuthResult> task);
    public void failed(Task<AuthResult> task);
}
