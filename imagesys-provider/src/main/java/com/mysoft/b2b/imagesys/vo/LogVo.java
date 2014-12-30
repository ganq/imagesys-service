package com.mysoft.b2b.imagesys.vo;

import com.google.code.morphia.annotations.Id;
import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Indexed;
import com.mysoft.b2b.imagesys.util.DateUtil;

@Entity(value="img_operation_log",noClassnameStored=true)
public class LogVo {
    @Id
    private String id;
	private String operator;
	
	@Indexed(name="t_img_operation_index")
	private String endTime;
	private String status;
	private String imgPath;
	private String operType;
	private String beginTime;
	private Long   imgSize;
	private String imgFormat;

    public LogVo() {

    }

	public LogVo(String imgFormat,Long imgSize,String operType,String beginTime,String status,String userId, String filePath){
		this.setOperator(userId);
		this.setBeginTime(beginTime);
		this.setEndTime(DateUtil.getNow());
		this.setImgPath(imgPath);
		this.setOperType(operType);
		this.setStatus(status);
		this.setImgFormat(imgFormat);
		this.setImgSize(imgSize);
		this.setId(new ObjectId().toString());
        this.imgPath = filePath;
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public Long getImgSize() {
		return imgSize;
	}

	public void setImgSize(Long imgSize) {
		this.imgSize = imgSize;
	}

	public String getImgFormat() {
		return imgFormat;
	}

	public void setImgFormat(String imgFormat) {
		this.imgFormat = imgFormat;
	}
	
	
}
