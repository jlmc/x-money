package com.xcosta.xmoney.api.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("xmoney")
public class XMoneyProperty {

    private Security security = new Security();
    private String origin = "http://localhost:8000";

    public Security getSecurity() {
        return security;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public static class Security {
        private boolean enableHttps;

        public boolean isEnableHttps() {
            return enableHttps;
        }

        public void setEnableHttps(boolean enableHttps) {
            this.enableHttps = enableHttps;
        }
    }
}
