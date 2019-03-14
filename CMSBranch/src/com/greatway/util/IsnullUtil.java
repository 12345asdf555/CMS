package com.greatway.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import com.greatway.model.AuthorityParameter;

public class IsnullUtil {
	/**
	 * 判断字符串是否为空，为空则返回false，不为空则返回true
	 * @param str
	 * @return
	 */
	public boolean isNull(String str){
		if(str==null || str.equals("")){
			return false;
		}
		return true;
	}
	
	/**
	 * webservice 权限认证
	 * @param client Client类
	 */
	public void Authority(Client client){
		AuthorityParameter param = new AuthorityParameter("userName", "admin", "password", "123456");
		client.getOutInterceptors().add(new AuthorityHeaderInterceptor(param)); 
		client.getOutInterceptors().add(new LoggingOutInterceptor()); 
		HTTPClientPolicy policy = ((HTTPConduit) client.getConduit()).getClient();
		policy.setConnectionTimeout(30000);
	  	policy.setReceiveTimeout(180000);
	}
	
	/**
	 * 获取某年的第几周的第一天和最后一天
	 * @return
	 */
	public String getWeekDay(int year,int week){
		String str = "";
		Calendar c = new GregorianCalendar();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DATE, 1);

		Calendar cal = (GregorianCalendar) c.clone();
		cal.add(Calendar.DATE, (week-2) * 7);

		Calendar ca = new GregorianCalendar();
		ca.setFirstDayOfWeek(Calendar.MONDAY);
		ca.setTime(cal.getTime());
		ca.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		str = sdf.format(ca.getTime()) + "/";

		SimpleDateFormat sdf2 = new SimpleDateFormat("dd");
		ca.set(Calendar.DATE, Integer.parseInt(sdf2.format(ca.getTime()))+7);
		str += sdf.format(ca.getTime());
		return str;
	}
}
