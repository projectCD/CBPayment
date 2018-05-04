package com.CBpayments.config;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.filter.CharacterEncodingFilter;
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
	    
	    @Override
		public void onStartup(ServletContext servletContext) throws ServletException {
			super.onStartup(servletContext);
			
			FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("characterEncodingFilter", new CharacterEncodingFilter());
	        encodingFilter.setInitParameter("encoding", "UTF-8");
	        encodingFilter.setInitParameter("forceEncoding", "true");
	        encodingFilter.addMappingForUrlPatterns(null, true, "/*");
			
		}
}
