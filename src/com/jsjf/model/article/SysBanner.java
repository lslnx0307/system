package com.jsjf.model.article;

import java.io.IOException;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jsjf.common.ConfigUtil;
import com.jsjf.common.PropertyUtil;
import com.jsjf.common.Utils;

public class SysBanner {
    /**
     * 主键  
     */
    private Long id;

    /**
     * 图片路径  
     */
    private String imgUrl;

    /**
     * 广告说明  
     */
    private String remark;

    /**
     * 广告标题  
     */
    private String title;

    /**
     * 状态 0-删除 1-有效
     */
    private Integer status;
    private String statusName;

    /**
     * 广告的位置
     */
    private Integer code;
    private String codeName;
    /**
     * 支持最低版本号
     */
    private String minVersion;

    /**
     * 排序字段 1-9 1为最后面 9为最前面  
     */
    private Integer sort;

    /**
     * 跳转地址  
     */
    private String location;

    /**
     * 颜色  
     */
    private String color;
    
    private Date startDate;
    private Date endDate;
    
    private String startDateStr;
    private String endDateStr;
    private Integer selectType;
    
    public Integer getSelectType() {
		return selectType;
	}

	public void setSelectType(Integer selectType) {
		this.selectType = selectType;
	}

	private Integer oldCode;

    public Integer getOldCode() {
		return oldCode;
	}

	public void setOldCode(Integer oldCode) {
		this.oldCode = oldCode;
	}

	public String getStartDateStr() {
    	return Utils.format(startDate, "yyyy-MM-dd HH:mm:ss");
	}

	public String getEndDateStr() {
		return Utils.format(endDate, "yyyy-MM-dd HH:mm:ss");
	}

	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  
    public Date getStartDate() {
		return startDate;
	}

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  
	public Date getEndDate() {
		return endDate;
	}
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusName() {
		try {
			statusName = ConfigUtil.dictionaryMap.get(
					PropertyUtil.getProperties("bannerstatus")).get(status);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

    public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getCodeName() {
		try {
			codeName = ConfigUtil.dictionaryMap.get(
					PropertyUtil.getProperties("bannercode")).get(code);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

	/**
	 * @return the minVersion
	 */
	public String getMinVersion() {
		return minVersion;
	}

	/**
	 * @param minVersion the minVersion to set
	 */
	public void setMinVersion(String minVersion) {
		this.minVersion = minVersion;
	}
}