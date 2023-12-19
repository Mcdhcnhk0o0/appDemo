package com.example.appdemo.network.helper;


import com.example.appdemo.network.protocol.UserServiceApi;
import com.example.appdemo.pojo.vo.UserInfoVO;


public class UserInfoHelper extends AbstractApiHelper<UserServiceApi> {

    @Override
    Class<? extends UserServiceApi> getInnerService() {
        return UserServiceApi.class;
    }

    public void getUserInfo(String userId, ApiResponse<UserInfoVO> userInfoResponse) {
        bindResponse(withService().getUserInformation(userId), userInfoResponse);
    }

}
