package com.example.appdemo.network.helper;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.appdemo.network.ServiceCreator;
import com.example.appdemo.network.response.ApiResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class AbstractApiHelper<S> {

    private static final String TAG = AbstractApiHelper.class.getSimpleName();

    protected S innerService;

    public interface ApiResponse<V> {
        void onSuccess(V vo);
        default void onError(String message) {
            Log.e(TAG, message);
        }
        default void onFail(Throwable t) {

        }
    }

    abstract Class<? extends S> getInnerService();

    protected S withService() {
        if (innerService == null) {
            innerService = ServiceCreator.create(getInnerService());
        }
        return innerService;
    }

    protected <V> void bindResponse(Call<ApiResult<V>> call, AbstractApiHelper.ApiResponse<V> apiResponse) {
        call.enqueue(new Callback<ApiResult<V>>() {
            @Override
            public void onResponse(@NonNull Call<ApiResult<V>> call, @NonNull Response<ApiResult<V>> response) {
                if (response.body() != null && response.body().getData() != null) {
                    apiResponse.onSuccess(response.body().getData());
                } else {
                    apiResponse.onError("errors in deserialization");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResult<V>> call, @NonNull Throwable t) {
                Log.e("AbstractApiHelper", "error: " + t + " in " + call.request().url());
                apiResponse.onFail(t);
            }
        });
    }


}
