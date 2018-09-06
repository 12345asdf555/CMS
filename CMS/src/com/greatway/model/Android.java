package com.greatway.model;

import java.math.BigInteger;

public class Android {
	private BigInteger id;
	private String weldedJunctionno;
	private String serialNo;
	private double maxElectricity;
	private double minElectricity;
	private double maxValtage;
	private double minValtage;
	private String wallThickness;
	private String externalDiameter;
	private String material;//材质（新增字段）
	private String nextexternaldiameter;//下游外径（新增字段）
	private String nextwall_thickness;
	private String next_material;
	private BigInteger itemid;
	private String itemname;
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public String getWeldedJunctionno() {
		return weldedJunctionno;
	}
	public void setWeldedJunctionno(String weldedJunctionno) {
		this.weldedJunctionno = weldedJunctionno;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public double getMaxElectricity() {
		return maxElectricity;
	}
	public void setMaxElectricity(double maxElectricity) {
		this.maxElectricity = maxElectricity;
	}
	public double getMinElectricity() {
		return minElectricity;
	}
	public void setMinElectricity(double minElectricity) {
		this.minElectricity = minElectricity;
	}
	public double getMaxValtage() {
		return maxValtage;
	}
	public void setMaxValtage(double maxValtage) {
		this.maxValtage = maxValtage;
	}
	public double getMinValtage() {
		return minValtage;
	}
	public void setMinValtage(double minValtage) {
		this.minValtage = minValtage;
	}
	public String getWallThickness() {
		return wallThickness;
	}
	public void setWallThickness(String wallThickness) {
		this.wallThickness = wallThickness;
	}
	public String getExternalDiameter() {
		return externalDiameter;
	}
	public void setExternalDiameter(String externalDiameter) {
		this.externalDiameter = externalDiameter;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getNextexternaldiameter() {
		return nextexternaldiameter;
	}
	public void setNextexternaldiameter(String nextexternaldiameter) {
		this.nextexternaldiameter = nextexternaldiameter;
	}
	public String getNextwall_thickness() {
		return nextwall_thickness;
	}
	public void setNextwall_thickness(String nextwall_thickness) {
		this.nextwall_thickness = nextwall_thickness;
	}
	public String getNext_material() {
		return next_material;
	}
	public void setNext_material(String next_material) {
		this.next_material = next_material;
	}
	public BigInteger getItemid() {
		return itemid;
	}
	public void setItemid(BigInteger itemid) {
		this.itemid = itemid;
	}
	public String getItemname() {
		return itemname;
	}
	public void setItemname(String itemname) {
		this.itemname = itemname;
	}
	
	
}
