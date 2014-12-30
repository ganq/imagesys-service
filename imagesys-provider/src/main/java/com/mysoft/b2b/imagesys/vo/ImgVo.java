package com.mysoft.b2b.imagesys.vo;

import java.io.File;

import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.mysoft.b2b.imagesys.util.DateUtil;
import com.mysoft.b2b.imagesys.util.FileUtil;

@Entity(value="img_image_metadata",noClassnameStored=true)
public class ImgVo {

    @Id
    private String id;
	private Long imgSize;
	private String imgType;
	private String creationTime;
	private String lastUpdateTime;
	private String md5;
    @Indexed
	private String imgPath;
	
	public ImgVo(File file){
		
		this.setImgType(FileUtil.getFileFormat(file));
		this.setImgSize(FileUtil.getFileSize(file));
		this.setCreationTime(DateUtil.getNow());
		this.setMd5(FileUtil.getMD5(file));
		this.setImgPath(file.getAbsolutePath());
		this.setId(new ObjectId().toString());
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getImgSize() {
		return imgSize;
	}

	public void setImgSize(Long imgSize) {
		this.imgSize = imgSize;
	}

	public String getImgType() {
		return imgType;
	}

	public void setImgType(String imgType) {
		this.imgType = imgType;
	}

	public String getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}

	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	
}
