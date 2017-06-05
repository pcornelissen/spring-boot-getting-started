package com.packtpub.yummy.ui;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component @Scope(value = "websocket",
        proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MyTestBean {
    String businessData="I am not set";

    public String getBusinessData() {
        return businessData;
    }

    public void setBusinessData(String businessData) {
        this.businessData = businessData;
    }
}
