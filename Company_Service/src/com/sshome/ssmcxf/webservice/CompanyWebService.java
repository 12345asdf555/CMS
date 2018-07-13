package com.sshome.ssmcxf.webservice;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface CompanyWebService {
	Object enterTheWS(@WebParam(name="obj1")String obj1,@WebParam(name="obj2")String obj2);
	Object enterTheIDU(@WebParam(name="obj1")String obj1,@WebParam(name="obj2")String obj2);
	Object enterNoParamWs(@WebParam(name="obj1")String obj1);
	Object enterChart(@WebParam(name="obj1")String obj1);
}
