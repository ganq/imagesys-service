package com.mysoft.b2b.imagesys.api;

import java.io.File;
import java.util.List;

/**
 * chengp:    图片操作接口
 * @version   Revision History
 * <pre>
 * Author     Version       Date        Changes
 * chengp    1.0           2014年9月9日     Created
 * </pre>
 * @since b2b
 */
public interface ImageService {

    /**
     * 创建文件 
     * @param userId
     * @param stream
     * @param fileName
     * @param filePath
     * @return
     */
    public int mkFile(String userId, String stream, String fileName, String filePath);

    /**
     * 创建文件 
     * @param userId
     * @param fileBytes
     * @param fileName
     * @param filePath
     * @return
     */
    public int mkFile(String userId, byte[] fileBytes, String fileName, String filePath);

    /**
     * 创建目录
     * @param userId
     * @param fileName
     * @param filePath
     * @return
     */
    public int mkdir(String userId, String fileName, String filePath);

    /**
     *浏览文件
     *@param  filterDirNames 过滤的目录 
     *@param  filePath  需要浏览的目录
     */
    public List<File> listFiles(String[] filterDirNames, String filePath);

    /**
     * 删除文件 ,如果是文件夹，则删除整个文件夹
     * @param userId
     * @param filePath
     * @return
     */
    public int removeFile(String userId, String filePath);
    
    /**
     * 移动文件 
     * @param userId
     * @param fileName
     * @param srcFilePath
     * @param targetFilePath
     * @return
     */
    public int moveFile(String userId, String fileName, String srcFilePath, String targetFilePath);

}
