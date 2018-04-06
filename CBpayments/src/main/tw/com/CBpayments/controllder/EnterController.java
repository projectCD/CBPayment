package com.CBpayments.controllder;


import java.util.List;

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

@Controller
@RequestMapping("/")
public class EnterController {
   
	private final Logger logger = LoggerFactory.getLogger(EnterController.class);
	private final CBpayServcieImp testService;

	@Autowired
	public EnterController(CBpayServcieImp testService) {
		this.testService = testService;
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
        
        name =  testService.getDesc(name);
        
        request.setAttribute("name", name);  
        
        System.out.println("name: " +  name);
        
        return ControllerUtil.CONTROLLER_INPUTDATE_PAGE;
    }
    
    
    
    @ResponseBody
	@RequestMapping(value = "/search" ,  method = RequestMethod.POST)
	public List<Student> getSearchResultViaAjax(HttpServletRequest request) {

    	System.out.println("ajax !!");
    	
    	List<Student> list = testService.searchStudentAll();
    	
    	list.forEach(e -> System.out.println(e.getSno()));
    	
    	
		return list;

	}
    
}