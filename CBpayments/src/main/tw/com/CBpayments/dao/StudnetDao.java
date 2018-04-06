package com.CBpayments.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.CBpayments.model.Student;

@Repository
@Qualifier("studnetDao")
public class StudnetDao implements Serializable{

	@Autowired
    JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
 
	public List <Student> findStudentAll() {
        List <Student> student = getJdbcTemplate().query("SELECT * FROM STUDENT", new BeanPropertyRowMapper(Student.class));
        return student;
    }
	

}
