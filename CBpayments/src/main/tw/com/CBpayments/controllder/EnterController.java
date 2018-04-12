package com.CBpayments.controllder;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.CBpayments.model.Student;
import com.CBpayments.serviceImp.CBpayServcieImp;
import com.CBpayments.util.ControllerUtil;
import com.CBpayments.wxpay.WXPayConstants;
import com.CBpayments.wxpay.WXPayConstants.SignType;
import com.CBpayments.wxpay.WXPayUtil;

@Controller
@RequestMapping("/")
public class EnterController {
   
	private final Logger logger = LoggerFactory.getLogger(EnterController.class);
	private final CBpayServcieImp cbPayService;

	@Autowired
	public EnterController(CBpayServcieImp cbPayService) {
		this.cbPayService = cbPayService;
	}
	
    @RequestMapping(method = RequestMethod.GET)
    public String sayHello(ModelMap model) {
        model.addAttribute("greeting", "Hello World from Spring 4 MVC");
        System.out.println("start welcome");
        
        logger.info("start welcome---EnterController");

        return ControllerUtil.CONTROLLER_WELCOME_PAGE;
    }
 
    @RequestMapping(value = "/helloagain", method = RequestMethod.GET)
    public String sayHelloAgain(HttpServletRequest request) {
        String name = request.getParameter("name");
        
        name =  cbPayService.getDesc(name);
        
        request.setAttribute("name", name);  
        
        System.out.println("name: " +  name);
        
        return ControllerUtil.CONTROLLER_INPUTDATE_PAGE;
    }
    
    
    @RequestMapping(value = "/goGoogle", method = RequestMethod.GET)
    public String goGoogle(HttpServletRequest request) {
    	
    	System.out.println("google");
    	
    	String redirectUrl = request.getScheme() + "://www.google.com.tw";
        return "redirect:" + redirectUrl;

    }
    
    
    @RequestMapping(value = "/xmlSubmit", method = RequestMethod.GET)
    public String xmlSubmit(HttpServletRequest request) {
    	
    	String mch_id = request.getParameter("mch_id");
    	String body = request.getParameter("body");
    	String total_fee = request.getParameter("total_fee");
    	
    	Map<String, String> map = new HashMap<String,String>();
		map.put("service", WXPayConstants.WXPAY_SERVICE);
		map.put("version", "1.0");
		map.put("charset", WXPayConstants.WXPAY_UTF8);
		map.put("sign_type", WXPayConstants.MD5);
		map.put("mch_id", mch_id);
		map.put("out_trade_no", WXPayUtil.generateOutNo());
		map.put("body", body);
		map.put("fee_type", WXPayConstants.WXPAY_CNY);
		map.put("total_fee", total_fee);
		map.put("mch_create_ip", "192.168.2.190");
		map.put("notify_url", "http//wx.unitepay.com.cn/onlinepay/sdjNotice");
		map.put("callback_url", "http//wx.unitepay.com.cn/onlinepay/success_pay.jsp");
		map.put("nonce_str", WXPayUtil.generateUUID());
    	
		Map<String, String> xmlMap = cbPayService.callPayComing(map, WXPayConstants.WXPAY_URL_3126);
		
		String url = xmlMap.get("code_url");
		System.out.println("url :" +  url);
		request.setAttribute("url", url);  
		
        return ControllerUtil.CONTROLLER_INPUTDATE_PAGE;

    }
    
    @ResponseBody
	@RequestMapping(value = "/search" ,  method = RequestMethod.POST)
	public List<Student> getSearchResultViaAjax(HttpServletRequest request) {

    	System.out.println("ajax !!");
    	
    	List<Student> list = cbPayService.searchStudentAll();
    	
    	list.forEach(e -> System.out.println(e.getSno()));
    	
    	
		return list;

	}
   
}