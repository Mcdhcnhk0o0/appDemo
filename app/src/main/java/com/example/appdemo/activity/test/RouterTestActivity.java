package com.example.appdemo.activity.test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.appdemo.R;
import com.example.appdemo.util.ToastUtil;
import com.example.router.ServiceManager;
import com.example.router.annotation.Router;

import java.util.List;
import java.util.Map;

@Router(url = "native://service_test", description = "远程调用测试")
public class RouterTestActivity extends AppCompatActivity {

    private Button remoteServiceCall;
    private TextView remoteServiceResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_router_test);
        remoteServiceCall = findViewById(R.id.remote_service_call);
        remoteServiceResult = findViewById(R.id.remote_service_result);
        initListener();
    }

    private void initListener() {
        remoteServiceCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callRemoteService();
            }
        });

    }

    private void callRemoteService() {
        String serviceClass = ServiceManager.getInstance().getService("application");
        List<String> serviceMethods = ServiceManager.getInstance().getMethods(serviceClass);
        for (String method: serviceMethods) {
            Object res = ServiceManager.getInstance().callService(serviceClass, method);
            remoteServiceResult.setText(res.toString());
            if (res instanceof Application) {
                ToastUtil.INSTANCE.show("get application succeed!");
            } else if (res instanceof Activity) {
                ToastUtil.INSTANCE.show("get top activity succeed!");
            }
        }
    }


}