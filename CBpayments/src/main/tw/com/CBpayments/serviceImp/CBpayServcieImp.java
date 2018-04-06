package com.CBpayments.serviceImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CBpayments.dao.StudnetDao;
import com.CBpayments.model.Student;
import com.CBpayments.service.CBpayService;

@Service
public class CBpayServcieImp implements CBpayService {
	
	 @Autowired
	 private StudnetDao studnetDao;
	 
	 private StudnetDao getDao() {
	        return studnetDao;
	    }
	 
	
	@Override
	public String getDesc(String name) {
		// TODO Auto-generated method stub
		return "Hello" + name;
	}

	@Override
	public List<Student> searchStudentAll() {
		// TODO Auto-generated method stub
		
		
		return getDao().findStudentAll();
	}

	
}
