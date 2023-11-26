package com.example.appdemo.network.helper;

import android.util.Log;

import com.example.appdemo.network.response.Result;
import com.example.appdemo.network.service.UserService;
import com.example.appdemo.pojo.vo.UserInfoVO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfoHelper {

    public interface UserInfoResponse {
        void onSuccess(UserInfoVO userInfoVO);
        void onError(String message);
        void onFail(Throwable t);
    }


    public static void getUserInfo(String userId, UserInfoResponse userInfoResponse) {
        bindResponse(UserService.getService().getUserInformation(userId), userInfoResponse);
    }

    private static void bindResponse(Call<Result<UserInfoVO>> call, UserInfoHelper.UserInfoResponse userInfoResponse) {
        call.enqueue(new Callback<Result<UserInfoVO>>() {
            @Override
            public void onResponse(Call<Result<UserInfoVO>> call, Response<Result<UserInfoVO>> response) {
                if (response.body() != null && response.body().getData() != null) {
                    userInfoResponse.onSuccess(response.body().getData());
                } else {
                    userInfoResponse.onError("errors in deserialization");
                }
            }

            @Override
            public void onFailure(Call<Result<UserInfoVO>> call, Throwable t) {
                Log.e("LoginHelper", "error: " + t + " in " + call.request().url());
                userInfoResponse.onFail(t);
            }
        });
    }

}
