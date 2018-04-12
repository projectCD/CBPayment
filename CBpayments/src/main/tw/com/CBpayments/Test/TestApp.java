package com.CBpayments.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.CBpayments.wxpay.WXPayUtil;






public class TestApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestApp app;
		try {
			app = new TestApp();
			//app.test001();
			
			Calendar c = Calendar.getInstance();
//			  System.out.println(c.get(Calendar.YEAR));
//			  System.out.println(c.get(Calendar.MONTH)+1);
//			  System.out.println(c.get(Calendar.DATE));
//			  System.out.println(c.get(Calendar.HOUR_OF_DAY));
//			  System.out.println(c.get(Calendar.MINUTE));
//			  System.out.println(c.get(Calendar.SECOND));
			  
			// String time = c.get(Calendar.YEAR) +  c.get(Calendar.MONTH)+1 +c.get(Calendar.DAY_OF_MONTH) + c.get(Calendar.HOUR_OF_DAY);
			
			  SimpleDateFormat nowdate = new java.text.SimpleDateFormat("yyyyMMddmmss"); 

//			  String sdate = nowdate.format(new java.util.Date());
//			  System.out.println(sdate);
			  
			String key ="31313131313131313131313131313131";  
			
			Map<String, String> map = new HashMap<String,String>();
			map.put("service", "pay.wechat.smpay");
			map.put("version", "1.0");
			map.put("charset", "UTF-8");
			map.put("sign_type", "MD5");
			map.put("mch_id", "500581200000001");
			map.put("out_trade_no", "20150806115340");
			map.put("body", "測試");
			map.put("fee_type", "CNY");
			map.put("total_fee", "10");
			map.put("mch_create_ip", "192.168.2.190");
			map.put("notify_url", "http//wx.unitepay.com.cn/onlinepay/sdjNotice");
			map.put("callback_url", "http//wx.unitepay.com.cn/onlinepay/success_pay.jsp");
			map.put("nonce_str", "65f73921f9f143bc9f412917c96ea720");
			
			System.out.println("time "  + WXPayUtil.generateOutNo());
			
			
			
			String sing = WXPayUtil.generateSignature(map,key);
			System.out.println("sign" +  sing);
			map.put("sign", sing);
			System.out.println("=======================================");
			String xml = WXPayUtil.mapToXml(map);
			
			
			System.out.println("xml "+ xml);
			  

		    
			String UTF8 = "UTF-8";

	        URL httpUrl = new URL("http://posp.paycoming.com/onlinepay/gateway");
	        HttpURLConnection httpURLConnection = (HttpURLConnection) httpUrl.openConnection();
//	        httpURLConnection.setRequestProperty("Host", "api.mch.weixin.qq.com");
	        httpURLConnection.setDoOutput(true);
	        httpURLConnection.setRequestMethod("POST");
	        httpURLConnection.setConnectTimeout(10*1000);
	        httpURLConnection.setReadTimeout(10*1000);
	        httpURLConnection.connect();
	        OutputStream outputStream = httpURLConnection.getOutputStream();
	        outputStream.write(xml.getBytes(UTF8));

	        InputStream inputStream = httpURLConnection.getInputStream();
	        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, UTF8));
	        final StringBuffer stringBuffer = new StringBuffer();
	        String line = null;
	        while ((line = bufferedReader.readLine()) != null) {
	            stringBuffer.append(line);
	        }
	        String resp = stringBuffer.toString();
	        if (stringBuffer!=null) {
	            try {
	                bufferedReader.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        if (inputStream!=null) {
	            try {
	                inputStream.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        if (outputStream!=null) {
	            try {
	                outputStream.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }

	        System.out.println(resp);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	
}
