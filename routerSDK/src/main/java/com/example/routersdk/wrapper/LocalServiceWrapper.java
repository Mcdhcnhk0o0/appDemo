package com.example.routersdk.wrapper;

import com.example.router.ServiceManager;
import com.example.routersdk.bean.ServiceRequest;
import com.example.routersdk.bean.ServiceResponse;
import com.example.routersdk.protocol.IService;

import java.util.List;

public class LocalServiceWrapper implements IService {

    public static LocalServiceWrapper getInstance() {
        return InnerClass.instance;
    }

    @Override
    public <T> ServiceResponse<T> call(ServiceRequest request) {
        ServiceResponse<T> response = new ServiceResponse<>();
        if (request == null || request.getMethodName() == null || request.getServiceName() == null) {
            response.setSuccess(false);
            response.setMessage("Neither service nor method cannot be null");
            return response;
        }
        String serviceClass = ServiceManager.getInstance().getService(request.getServiceName());
        if (serviceClass == null) {
            response.setSuccess(false);
            response.setMessage("Service not found");
            return response;
        }
        List<String> methods = ServiceManager.getInstance().getMethods(serviceClass);
        if (methods == null || !methods.contains(request.getMethodName())) {
            response.setSuccess(false);
            response.setMessage("Method not found");
            return response;
        }
        Object result = null;
        try {
            result = ServiceManager.getInstance().callService(request.getServiceName(), request.getMethodName());
            if (result != null) {
                response.setResult((T) result);
            }
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            return response;
        }
        return response;
    }

    private static final class InnerClass {
        private static final LocalServiceWrapper instance = new LocalServiceWrapper();
    }

}
