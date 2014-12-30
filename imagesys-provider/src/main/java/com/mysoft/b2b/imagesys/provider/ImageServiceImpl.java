package com.mysoft.b2b.imagesys.provider;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;

import com.mysoft.b2b.imagesys.api.ImageOperCode;
import com.mysoft.b2b.imagesys.api.ImageService;
import com.mysoft.b2b.imagesys.service.MongoService;
import com.mysoft.b2b.imagesys.util.DateUtil;
import com.mysoft.b2b.imagesys.util.FileUtil;
import com.mysoft.b2b.imagesys.util.ImageOperStatusEnum;
import com.mysoft.b2b.imagesys.util.ImageOperTypeEnum;
import com.mysoft.b2b.imagesys.vo.ImgVo;
import com.mysoft.b2b.imagesys.vo.LogVo;

public class ImageServiceImpl implements ImageService {

    private static Logger logger = Logger.getLogger(ImageServiceImpl.class);

    private MongoService mongoService;

    private String imageOrgRoot;

    private String imageThumbnailRoot;

    private String imageRoot;

    public String getImageRoot() {
        return imageRoot;
    }

    public void setImageRoot(String imageRoot) {
        this.imageRoot = imageRoot;
    }

    public String getImageOrgRoot() {
        return imageOrgRoot;
    }

    public void setImageOrgRoot(String imageOrgRoot) {
        this.imageOrgRoot = imageOrgRoot;
    }

    public String getImageThumbnailRoot() {
        return imageThumbnailRoot;
    }

    public void setImageThumbnailRoot(String imageThumbnailRoot) {
        this.imageThumbnailRoot = imageThumbnailRoot;
    }

    public MongoService getMongoService() {
        return mongoService;
    }

    public void setMongoService(MongoService mongoService) {
        this.mongoService = mongoService;
    }

    public int mkFile(String userId, String stream, String fileName, String filePath) {
        try {
            byte[] buffer = (new BASE64Decoder()).decodeBuffer(stream);
            return mkFile(userId, buffer, fileName, filePath);
        } catch (IOException e) {
            logger.error("Unknown error.", e);
        }
        return ImageOperCode.FAILURE;
    }

    public int mkFile(String userId, byte[] fileBytes, String fileName, String filePath) {
        String beginTime = DateUtil.getNow();
        String path = filePath.concat(File.separator).concat(fileName);
        File newFile = new File(path);
        try {
            if (newFile.exists()) {
                int index = fileName.lastIndexOf(".");
                removeThumbnail(filePath, fileName.substring(0, index));
            }
            FileUtils.writeByteArrayToFile(newFile, fileBytes);
            mongoService.saveModel(new ImgVo(newFile));
            mongoService.saveModel(new LogVo(FileUtil.getFileFormat(newFile), FileUtil.getFileSize(newFile), ImageOperTypeEnum.UPLOAD
                    .value(), beginTime, ImageOperStatusEnum.SUCCESS.value(), userId, filePath));
            return ImageOperCode.SUCCESS;
        } catch (Exception e) {
            logger.error("Unknown error.", e);
            mongoService.saveModel(new LogVo(FileUtil.getFileFormat(newFile), FileUtil.getFileSize(newFile), ImageOperTypeEnum.UPLOAD
                    .value(), beginTime, ImageOperStatusEnum.FAILURE.value(), userId, filePath));
        }
        return ImageOperCode.FAILURE;
    }

    public int mkdir(String userId, String fileName, String filePath) {
        try {
            String beginTime = DateUtil.getNow();
            String dirPath = filePath.concat(File.separator).concat(fileName);
            File dir = new File(dirPath);
            if (dir.exists()) {
                return ImageOperCode.EXISTED;
            }
            FileUtils.forceMkdir(dir);
            mongoService.saveModel(new LogVo("dir", 0l, ImageOperTypeEnum.ADD.value(), beginTime, ImageOperStatusEnum.SUCCESS.value(),
                    userId, filePath));
            return ImageOperCode.SUCCESS;

        } catch (IOException e) {
            logger.error("Unknown error.", e);
            ;
        }
        return ImageOperCode.FAILURE;
    }

    public int removeFile(String userId, String filePath) {
        String beginTime = DateUtil.getNow();
        File file = new File(filePath);
        String imgFormat = "";
        Long imgSize = 0l;
        try {
            if (!file.exists()) {
                return ImageOperCode.NOTEXISTED;
            }
            if (file.isDirectory()) {
                File thumbnailFiles = new File(filePath.replace(this.imageOrgRoot, this.imageThumbnailRoot));
                FileUtils.deleteDirectory(thumbnailFiles);
            } else {
                mongoService.saveModel(new ImgVo(file));
                imgFormat = FileUtil.getFileFormat(file);
                imgSize = FileUtil.getFileSize(file);
                String fileName = file.getName();
                int index = fileName.lastIndexOf(".");
                removeThumbnail(file.getParent(), fileName.substring(0, index));
                FileUtils.forceDelete(file);
            }
            mongoService.saveModel(new LogVo(imgFormat, imgSize, ImageOperTypeEnum.DELETE.value(), beginTime, ImageOperStatusEnum.SUCCESS
                    .value(), userId, filePath));
            return ImageOperCode.SUCCESS;

        } catch (IOException e) {
            logger.error("Unknown error.", e);
            ;
            mongoService.saveModel(new LogVo(imgFormat, imgSize, ImageOperTypeEnum.DELETE.value(), beginTime, ImageOperStatusEnum.FAILURE
                    .value(), userId, filePath));

        }
        return ImageOperCode.FAILURE;
    }

    public List<File> listFiles(final String[] filterDirNames, String filePath) {

        final List<File> files = new ArrayList<File>();
        if (!StringUtils.isEmpty(filePath) && filePath.contains(this.imageRoot)) {
            File file = new File(filePath);
            if (file.isDirectory()) {
                file.listFiles(new FileFilter() {
                    @Override
                    public boolean accept(File file) {
                        if (null != filterDirNames && filterDirNames.length != 0) {
                            boolean added = true;
                            for (String filterName : filterDirNames) {
                                if (file.getAbsolutePath().contains(filterName)) {
                                    added = false;
                                    break;
                                }
                            }
                            if (added) {
                                files.add(file);
                            }
                            return added;
                        }
                        files.add(file);
                        return true;
                    }
                });
            }
        }
        return files;
    }

    private void removeThumbnail(String oriImagePath, String fileName) throws IOException {
        File thumbnailFiles = new File(oriImagePath.replace(this.imageOrgRoot, this.imageThumbnailRoot));
        if (thumbnailFiles.exists()) {
            File[] children = thumbnailFiles.listFiles();
            for (File child : children) {
                if (child.getName().startsWith(fileName.concat("_"))) {
                    FileUtils.forceDelete(child);
                }
            }
        }
    }

    @Override
    public int moveFile(String userId, String fileName, String srcFilePath, String targetFilePath) {
        String beginTime = DateUtil.getNow();
        String path = targetFilePath.concat(File.separator).concat(fileName);
        File newFile = new File(path);
        try {
            if (newFile.exists()) {
                int index = fileName.lastIndexOf(".");
                removeThumbnail(targetFilePath, fileName.substring(0, index));
            }
            File srcFile = new File(srcFilePath);
            if(!srcFile.exists()){
                throw new IllegalArgumentException("错误的原文件路径,file path = " + srcFilePath);
            }
            FileUtils.copyFile(srcFile, newFile);
            
            mongoService.saveModel(new ImgVo(newFile));
            mongoService.saveModel(new LogVo(FileUtil.getFileFormat(newFile), FileUtil.getFileSize(newFile), ImageOperTypeEnum.UPLOAD
                    .value(), beginTime, ImageOperStatusEnum.SUCCESS.value(), userId, targetFilePath));
            return ImageOperCode.SUCCESS;
        } catch (Exception e) {
            logger.error("Unknown error.", e);
            mongoService.saveModel(new LogVo(FileUtil.getFileFormat(newFile), FileUtil.getFileSize(newFile), ImageOperTypeEnum.UPLOAD
                    .value(), beginTime, ImageOperStatusEnum.FAILURE.value(), userId, targetFilePath));
        }
        return ImageOperCode.FAILURE;
    }

}
