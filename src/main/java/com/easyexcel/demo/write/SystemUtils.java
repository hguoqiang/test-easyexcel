package com.easyexcel.demo.write;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang3.StringUtils;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.time.ZoneId;

/**
 * @author lingzhen on 17/8/31.
 */
public class SystemUtils {

    /**
     * 应用配置信息
     */
    private static Configuration config = null;

    static {
        try {
            config = new PropertiesConfiguration(SystemUtils.class.getResource("/application.properties"));
        } catch (Exception e) {
            // 忽略异常
        }
    }

    /**
     * 获取应用名
     *
     * @return
     */
    public static String getApplicationName() {
        // 获取spring的应用名
        String applicationName = getString("spring.application.name");

        if (StringUtils.isEmpty(applicationName)) {
            // 如果不存在则取应用名
            applicationName = getString("application.name");
        }

        return applicationName;
    }

    public static String getHostName() {
        return NetUtils.getHostName();
    }

    /**
     * 获取应用服务器端地址(ip)
     *
     * @return
     */
    public static String getServerAddress() {
        return NetUtils.getHostAddress();
    }

    /**
     * 获取应用服务器端完整地址(ip:port)
     *
     * @return
     */
    public static String getServerFullAddress() {
        String fullAddress = NetUtils.getHostAddress();
        Integer port = getServerPort();
        if (port != null) {
            fullAddress += ":" + port;
        }
        return fullAddress;
    }

    /**
     * 获取应用服务器端口号
     *
     * @return
     */
    public static Integer getServerPort() {
        String portStr = System.getProperty("server.port");
        if (StringUtils.isEmpty(portStr)) {
            portStr = getString("server.port");
        }
        if (StringUtils.isEmpty(portStr)) {
            return null;
        }
        return Integer.parseInt(portStr);
    }

    /**
     * 是否是生产环境
     *
     * @return
     */
    public static boolean isProd() {
        String env = getString("spring.profiles.active");
        return StringUtils.startsWith(env, "prod");
    }

    /**
     * 获取币种
     *
     * @return
     */
    public static CurrencyUnit getCurrency() {
       // String currency = getString("server.currency");
        String currency = "CNY";
        return Monetary.getCurrency(StringUtils.isEmpty(currency) ? "CNY" : currency);
    }

    /**
     * 获取时区
     *
     * @return
     */
    public static ZoneId getZoneId() {
        String zoneId = getString("server.zoneId");
        return StringUtils.isEmpty(zoneId) ? ZoneId.systemDefault() : ZoneId.of(zoneId);
    }

    /**
     * 获取属性
     *
     * @param key
     * @return
     */
    public static String getString(String key) {
        // 先从SystemProperty里取
        String value = System.getProperty(key);
        if (!StringUtils.isEmpty(value)) {
            return value;
        }

        // 取不到再从配置文件里取
        if (config != null) {
            value = config.getString(key);
        }
        if (!StringUtils.isEmpty(value)) {
            return value;
        }

        // 再取不到再从环境变量里取
        try {
            Class envClazz = Class.forName("com.kaffatech.mocha.frame.application.EnvironmentUtils");
            value = (String) envClazz.getMethod("getString", String.class).invoke(null, key);
        } catch (Exception e) {
//            throw new IllegalArgumentException(e.getMessage(), e);
        }

        return value == null ? "" : value;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(getServerFullAddress());
        System.out.println(StringUtils.startsWith("prod", "prod"));
    }

}
