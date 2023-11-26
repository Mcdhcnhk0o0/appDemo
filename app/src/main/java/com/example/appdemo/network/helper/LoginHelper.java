package com.example.appdemo.network.helper;

import android.util.Log;

import com.example.appdemo.pojo.vo.LoginVO;
import com.example.appdemo.network.response.Result;
import com.example.appdemo.network.service.LoginService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginHelper {

    public interface LoginResponse {
        void onSuccess(LoginVO loginVO);
        void onError(String message);
        void onFail(Throwable t);
    }

    public static void signUp(String email, String nickname, String password, LoginResponse loginResponse) {
        bindResponse(LoginService.getService().signUp(email, nickname, password), loginResponse);
    }

    public static void login(String email, String password, LoginResponse loginResponse) {
        bindResponse(LoginService.getService().loginByEmail(email, password), loginResponse);
    }

    public static void logout(String userId, LoginResponse loginResponse) {
        bindResponse(LoginService.getService().logout(userId), loginResponse);
    }

    private static void bindResponse(Call<Result<LoginVO>> call, LoginResponse loginResponse) {
        call.enqueue(new Callback<Result<LoginVO>>() {
            @Override
            public void onResponse(Call<Result<LoginVO>> call, Response<Result<LoginVO>> response) {
                if (response.body() != null && response.body().getData() != null) {
                    loginResponse.onSuccess(response.body().getData());
                } else {
                    loginResponse.onError("errors in deserialization");
                }
            }

            @Override
            public void onFailure(Call<Result<LoginVO>> call, Throwable t) {
                Log.e("LoginHelper", "error: " + t + " in " + call.request().url());
                loginResponse.onFail(t);
            }
        });
    }



}
