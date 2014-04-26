package com.zl.manage;

import java.sql.Date;

import org.apache.struts.action.ActionForm;

public class ContentForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	private int systemid;
	private String title;
	private String remark;
	private String u_creater;
	private String r_media;
	private String content;
	private int templateid;
	private int channelid;
	private Date dt_create;
	private Date dt_update;
	private String op_commit;
	private String contentstring;
	private String path;
	private String articleId;
	private String personIdSet;

	public String getPersonIdSet() {
		return personIdSet;
	}

	public void setPersonIdSet(String personIdSet) {
		this.personIdSet = personIdSet;
	}

	public int getChannelid() {
		return channelid;
	}

	public void setChannelid(int channelid) {
		this.channelid = channelid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDt_create() {
		return dt_create;
	}

	public void setDt_create(Date dt_create) {
		this.dt_create = dt_create;
	}

	public Date getDt_update() {
		return dt_update;
	}

	public void setDt_update(Date dt_update) {
		this.dt_update = dt_update;
	}

	public String getOp_commit() {
		return op_commit;
	}

	public void setOp_commit(String op_commit) {
		this.op_commit = op_commit;
	}

	public String getR_media() {
		return r_media;
	}

	public void setR_media(String r_media) {
		this.r_media = r_media;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getSystemid() {
		return systemid;
	}

	public void setSystemid(int systemid) {
		this.systemid = systemid;
	}

	public int getTemplateid() {
		return templateid;
	}

	public void setTemplateid(int templateid) {
		this.templateid = templateid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getU_creater() {
		return u_creater;
	}

	public void setU_creater(String u_creater) {
		this.u_creater = u_creater;
	}

	public String getContentstring() {
		return contentstring;
	}

	public void setContentstring(String contentstring) {
		this.contentstring = contentstring;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleid) {
		this.articleId = articleid;
	}
}