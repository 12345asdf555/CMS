package com.greatway.model;

import java.math.BigInteger;

public class SmsUser {
	private int fid;
	private String fname;
	private String femail;
	private String fphone;
	private BigInteger fitemid;
	private String fitemname;
	private BigInteger fcreator;
	private BigInteger fmodifier;
	private BigInteger fuserid;
	
	public BigInteger getFuserid() {
		return fuserid;
	}
	public void setFuserid(BigInteger fuserid) {
		this.fuserid = fuserid;
	}
	public BigInteger getFcreator() {
		return fcreator;
	}
	public void setFcreator(BigInteger fcreator) {
		this.fcreator = fcreator;
	}
	public BigInteger getFmodifier() {
		return fmodifier;
	}
	public void setFmodifier(BigInteger fmodifier) {
		this.fmodifier = fmodifier;
	}
	public int getFid() {
		return fid;
	}
	public void setFid(int fid) {
		this.fid = fid;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getFemail() {
		return femail;
	}
	public void setFemail(String femail) {
		this.femail = femail;
	}
	public String getFphone() {
		return fphone;
	}
	public void setFphone(String fphone) {
		this.fphone = fphone;
	}
	public BigInteger getFitemid() {
		return fitemid;
	}
	public void setFitemid(BigInteger fitemid) {
		this.fitemid = fitemid;
	}
	public String getFitemname() {
		return fitemname;
	}
	public void setFitemname(String fitemname) {
		this.fitemname = fitemname;
	}
	
}
