package com.CBpayments.service;

import java.util.List;
import java.util.Map;

import com.CBpayments.model.Student;

public interface CBpayService {
   public String getDesc(String name) ;
   public List<Student> searchStudentAll();
   public Map<String , String> callPayComing(Map<String , String> map , String apiUrl); //呼叫paycoming api
}
