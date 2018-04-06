package com.CBpayments.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class ApInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{
	 @Override
	    protected Class<?>[] getRootConfigClasses() {
	        return new Class[] { ApRootConfig.class };
	    }
	  
	    @Override
	    protected Class<?>[] getServletConfigClasses() {
	    	return new Class[] { ApConfig.class };
	    }
	  
	    @Override
	    protected String[] getServletMappings() {
	        return new String[] { "/" };
	    }
}
