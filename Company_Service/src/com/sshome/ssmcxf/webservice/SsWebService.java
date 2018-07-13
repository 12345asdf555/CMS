package com.sshome.ssmcxf.webservice;

import java.math.BigInteger;

public interface SsWebService {
	
	public BigInteger FindIns_Id(String insname);
	public Boolean AddWeld(String obj);
	public Boolean UpdateWeld(String obj);
	public Boolean AddJunction(String obj);
	public Boolean UpdateJunction(String obj);
	public Boolean DeleteJunction(String obj);
	
}
