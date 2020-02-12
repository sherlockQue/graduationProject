package com.a;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@RunWith(SpringRunner.class)
@SpringBootTest
public class test {

	@Test
	public void t1() {
		
		JSONObject ojb=(JSONObject)JSON.parse("{'ak':'afdadf','234':'asdfasdf'}");
		
		System.out.println(ojb.get("ak"));
		
	}
	 
      
	 
		

}
