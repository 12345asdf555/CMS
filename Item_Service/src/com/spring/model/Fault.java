package com.spring.model;

import java.math.BigInteger;

public class Fault {
	private BigInteger id;
	private BigInteger itemid;
	private BigInteger machineid;
	private String code;
	private String type;
	private int typeid;
	private int codeid;
	private String machineno;
	private String time;
	private String desc;
	private String creator;
	private String modifier;
	
	public String getMachineno() {
		return machineno;
	}
	public void setMachineno(String machineno) {
		this.machineno = machineno;
	}
	public BigInteger getMachineid() {
		return machineid;
	}
	public void setMachineid(BigInteger machineid) {
		this.machineid = machineid;
	}
	public BigInteger getItemid() {
		return itemid;
	}
	public void setItemid(BigInteger itemid) {
		this.itemid = itemid;
	}
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getTypeid() {
		return typeid;
	}
	public void setTypeid(int typeid) {
		this.typeid = typeid;
	}
	public int getCodeid() {
		return codeid;
	}
	public void setCodeid(int codeid) {
		this.codeid = codeid;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getModifier() {
		return modifier;
	}
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
}
