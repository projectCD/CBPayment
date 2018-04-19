package com.CBpayments.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.CBpayments.model.Student;

@Repository
@Qualifier("studnetDao")
public class StudnetDao implements Serializable{
 
	public List <Student> findStudentAll() {
        List <Student> student = null;
        return student;
    }
	
	
	

}
