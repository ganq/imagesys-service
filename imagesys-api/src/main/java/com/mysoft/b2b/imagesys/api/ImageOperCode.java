package com.mysoft.b2b.imagesys.api;

/**
 * 
 * chengp:    图片操作结果值
 * @version   Revision History
 * <pre>
 * Author     Version       Date        Changes
 * chengp     1.0           2014年9月9日       Created
 *
 * </pre>
 * @since b2b
 */
public class ImageOperCode {

    /**
     * 操作成功
     */
    public final static int SUCCESS = 200;
    /**
     * 文件或者目录已存在
     */
    public final static int EXISTED = 201;
    /**
     * 操作失败
     */
    public final static int FAILURE = 202;
    /**
     * 禁止操作
     */
    public final static int FORBIDDEN = 203;
    /**
     * 文件或目录不存在
     */
    public final static int NOTEXISTED = 204;
    
}
