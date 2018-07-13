package com.sshome.ssmcxf.webservice;

import java.math.BigInteger;

public interface SsWebService {
	
	public BigInteger FindIns_Id(String insname);
	public Boolean AddWeld(String obj1,String obj2);
	public Boolean UpdateWeld(String obj1,String obj2);
	public Boolean AddJunction(String obj1,String obj2);
	public Boolean UpdateJunction(String obj1,String obj2);
	public Boolean DeleteJunction(String obj1,String obj2);
	
}
