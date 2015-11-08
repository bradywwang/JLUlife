package com.brady.jlulife.Models.Listener;

/**
 * Created by brady on 15-11-8.
 */
public interface LoginListener {
    public void onLoginSuccess();
    public void onLoginFailure(String failReason);
}
