package com.cyberway.core.acegi;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: cac Date: 2006-3-15
 */
public class AcegiConstants {

    public static final String STATUS_INVALID = "0";
    public static final String STATUS_VALID = "1";

    public static final String STATUS_AUTH = "1";
    public static final String STATUS_UNAUTH = "0";

    public static final String RESOURCE_URL = "URL";
    public static final String RESOURCE_FUNCTION = "FUNCTION";
    public static final String RESOURCE_COMPONENT = "TAG";
    /**
     * 匿名用户角色代码
     */
    public static final String ANONYMOUSUSER_CODE = "anonymousUser";

    /** A String variable : 分隔用户名与机构代码的标志符 */
    public static final String KEY_USERNAME_AND_SITEID_FLAG = "_and_";    
    public static Map statusEnum() {
        Map statusEnum = new HashMap();
        statusEnum.put(STATUS_INVALID, "无效");
        statusEnum.put(STATUS_VALID, "有效");
        return statusEnum;
    }

    public static Map resTypeEnum() {
        Map resTypeEnum = new HashMap();
        resTypeEnum.put("URL", RESOURCE_URL);
        resTypeEnum.put("FUNCTION", RESOURCE_FUNCTION);
        resTypeEnum.put("COMPONENT", RESOURCE_COMPONENT);
        return resTypeEnum;
    }

    public static Map authEnum() {
        Map authEnum = new HashMap();
        authEnum.put(STATUS_AUTH, "已授权");
        authEnum.put(STATUS_UNAUTH, "未授权");
        return authEnum;
    }

}
