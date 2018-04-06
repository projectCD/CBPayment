package com.CBpayments.service;

import java.util.List;

import com.CBpayments.model.Student;

public interface CBpayService {
   public String getDesc(String name) ;
   public List<Student> searchStudentAll();
}
