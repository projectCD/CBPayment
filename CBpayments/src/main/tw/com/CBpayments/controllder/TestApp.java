package com.CBpayments.controllder;

import java.util.Map;

import com.CBpayments.util.WXPayUtil;

public class TestApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String list = "<xml><service>pay.wechat.jspay</service><version>1.0</version><charset>UT\r\n" + 
				"F-8</charset><sign_type>MD5</sign_type><mch_id>886100000000002</mch_i" + 
				"d><out_trade_no>20161005175931</out_trade_no><body> 测 试\r\n" + 
				"</body><total_fee>2</total_fee><fee_type>CNY</fee_type><mch_create_ip>1" + 
				"92.168.2.190</mch_create_ip><notify_url>http://wx.unitepay.com.cn/onlinepay/s" + 
				"djNotice</notify_url><callback_url><![CDATA[http://wx.unitepay.com.cn/onlinep" + 
				"ay/success_pay.jsp]]></callback_url><nonce_str>65f73921f9f143bc9f412917c96e" + 
				"a716</nonce_str><sign><![CDATA[E16C656E6E6A18CF710978E66093EE2D]]></si" + 
				"gn></xml>";
		
		WXPayUtil xmlPay = new WXPayUtil();
		
		try {
			Map<String, String>  xml = xmlPay.xmlToMap(list);
			for (Map.Entry<String, String> entry : xml.entrySet()) {
	            System.out.println(entry.getValue());
	        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
