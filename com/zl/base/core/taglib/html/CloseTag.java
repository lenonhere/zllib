package com.zl.base.core.taglib.html;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CloseTag implements Tag{
	private String name;
	private String innerHTML;
	private Map attributes = new HashMap();
	public CloseTag(String name){
		this.name = name;
	}
	public void setInnerHTML(String innerHTML){
		this.innerHTML = innerHTML;
	}
	public void addInnerHTML(String innerHTML){
		if(innerHTML == null) return;
		if(this.innerHTML == null) this.innerHTML = "";
		this.innerHTML += "\n"+innerHTML; 
	}
	public void setAttribute(String attrName,String attrValue){
		attributes.put(attrName, attrValue);
	}
	public String toString(){
		StringBuffer sb = new StringBuffer("<"+this.name);
		Iterator it = attributes.keySet().iterator();
		while(it.hasNext()){
			String attrName = (String)it.next();
			String attrValue = (String)attributes.get(attrName);
			if(attrValue != null){
				sb.append(" ").append(attrName).append("=\"").append(attrValue).append("\"");
			}
		}
		sb.append(">\n");
		sb.append(this.innerHTML==null?"":this.innerHTML).append("\n");
		sb.append("</").append(this.name).append(">").append("\n");
		return sb.toString();
	}
	public String getAttribute(String attrName) {
		return (String)this.attributes.get(attrName);
	}
	public String getInnerHTML(){
		return this.innerHTML;
	}
}
