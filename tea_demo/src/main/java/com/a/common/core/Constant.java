
package com.a.common.core;

/**
 *  系统常量
 *
 */
public class Constant {
	
	/** 超级管理员ID */
	public static final int SUPER_ADMIN = 1;
	


    /**
     * 当前页码
     */
    public static final String PAGE = "page";
    /**
     * 每页显示记录数
     */
    public static final String LIMIT = "limit";
    /**
     * 排序字段
     */
    public static final String ORDER_FIELD = "sidx";
    /**
     * 排序方式
     */
    public static final String ORDER = "order";
    /**
     *  升序
     */
    public static final String ASC = "asc";



	/**
	 * 菜单类型
	 */
    public enum MenuType {
        /**
         * 目录
         */
    	CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    
    
    /**
     * 缓存key
     * 
     * https://blog.csdn.net/weixin_34018169/article/details/85988850
     * 
     */
    public static String getSysConfigKey(String key){
        return "sys:config:" + key;
    }

    
    public static String getSysDeptKey(){
        return "sys:dept:listall";
    }
    
    public static String getSysDictKey(String key){
        return "sys:dict:" + key;
    }
    
    
   

}
