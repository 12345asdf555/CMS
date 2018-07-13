package com.spring.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.greatway.manager.WeldingMachineManager;
import com.greatway.model.WeldingMachine;
import com.greatway.page.Page;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/config/spring-common.xml")
public class SpringTest {

	@Resource
	WeldingMachineManager w;
	
	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public final void testGetWeldingMachineAll() {
		Page page = new Page(1,1,0);
		List<WeldingMachine> list = w.getWeldingMachineAll(page,null,null);
		for(WeldingMachine we : list){
		}
	}
	
	 public static void main(String[] args) {
		 ApplicationContext ctx = new ClassPathXmlApplicationContext("config/spring-common.xml");
		 Object userMapper = ctx.getBean("userMapper");
		 Object roleMapper = ctx.getBean("roleMapper");
		 Object dataMapper = ctx.getBean("dataMapper");
		 System.out.println(userMapper);
		 System.out.println(roleMapper);
	 }
}
