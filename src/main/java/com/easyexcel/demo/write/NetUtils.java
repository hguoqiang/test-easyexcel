package com.easyexcel.demo.write;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 网络工具
 *
 * @author zsp
 * @date 2016-03-11
 */
public class NetUtils {

    /**
     * 取本机的机器名称
     *
     * @return
     * @throws UnknownHostException
     */
    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * 取本机的IP
     *
     * @return
     * @throws UnknownHostException
     */
    public static String getHostAddress() {
        try {
            String envIp = System.getProperty("LOCAL_IP");
            if (envIp != null && envIp.length() > 0) {
                return envIp;
            }
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * 取本机的地址信息
     *
     * @return
     * @throws UnknownHostException
     */
    public static InetAddress getLocalHost() {
        try {
            return InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new IllegalStateException(e);
        }
    }

}
