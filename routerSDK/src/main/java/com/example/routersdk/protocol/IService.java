package com.example.routersdk.protocol;

import com.example.routersdk.bean.ServiceRequest;
import com.example.routersdk.bean.ServiceResponse;

public interface IService {

    public <T> ServiceResponse<T> call(ServiceRequest request);

}
