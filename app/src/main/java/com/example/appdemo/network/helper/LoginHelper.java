package com.example.appdemo.network.helper;

import com.example.appdemo.network.protocol.LoginServiceApi;
import com.example.appdemo.pojo.vo.LoginVO;

public class LoginHelper extends AbstractApiHelper<LoginServiceApi> {

    @Override
    Class<? extends LoginServiceApi> getInnerService() {
        return LoginServiceApi.class;
    }

    public void signUp(String email, String nickname, String password, ApiResponse<LoginVO> loginResponse) {
        bindResponse(withService().signUp(email, nickname, password), loginResponse);
    }

    public void login(String email, String password, ApiResponse<LoginVO> loginResponse) {
        bindResponse(withService().loginByEmail(email, password), loginResponse);
    }

    public void logout(String userId, ApiResponse<LoginVO> loginResponse) {
        bindResponse(withService().logout(userId), loginResponse);
    }

}
