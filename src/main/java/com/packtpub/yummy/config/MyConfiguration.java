package com.packtpub.yummy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "my")
public class MyConfiguration {
    private String key;
    private double fraction;
    private List<URL> links;
    private HostData host;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public double getFraction() {
        return fraction;
    }

    public void setFraction(double fraction) {
        this.fraction = fraction;
    }

    public List<URL> getLinks() {
        return links;
    }

    public void setLinks(List<URL> links) {
        this.links = links;
    }

    public HostData getHost() {
        return host;
    }

    public void setHost(HostData host) {
        this.host = host;
    }

    @Override
    public String toString() {
        return "MyConfiguration{" +
                "key='" + key + '\'' +
                ", fraction=" + fraction +
                ", links=" + links +
                ", host=" + host +
                '}';
    }

    public static class HostData {
        String ip;
        int port;

        @Override
        public String toString() {
            return "HostData{" +
                    "ip='" + ip + '\'' +
                    ", port=" + port +
                    '}';
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }
    }
}
