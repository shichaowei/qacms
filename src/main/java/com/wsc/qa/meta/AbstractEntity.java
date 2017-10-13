package com.wsc.qa.meta;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public abstract class AbstractEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * @Description 备注
	 */
	protected String remarks;

	/**
	 * @Description 创建者
	 */
	protected String createBy;

	/**
	 * @Description 创建日期
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	protected Date createDate;

	/**
	 * @Description 更新者
	 */
	protected String updateBy;

	/**
	 * @Description 更新日期
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	protected Date updateDate;

	/**
	 * @Description 删除标记
	 */
	protected Boolean delFlag = false;

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks == null ? null : remarks.trim();
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy == null ? null : createBy.trim();
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy == null ? null : updateBy.trim();
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Boolean getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Boolean delFlag) {
		this.delFlag = delFlag;
	}

	/**
	 * @Title primaryKey
	 * @Description 获取主键
	 * @Author JieHong
	 * @Date 2016年2月27日 下午1:34:18
	 * @return
	 */
	public abstract String primaryKey();

	/**
	 * @Title setId
	 * @Description 创建Id
	 * @Author JieHong
	 * @Date 2016年3月8日 上午10:08:11
	 * @param id
	 */
	public abstract void setId(String id);
}