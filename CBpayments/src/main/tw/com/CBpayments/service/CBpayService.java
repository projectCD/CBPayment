package com.CBpayments.service;

import java.util.List;
import java.util.Map;

import com.CBpayments.model.CbLog;
import com.CBpayments.model.CbCustomer;
import com.CBpayments.model.CbTranDetails;
import com.CBpayments.model.Student;

public interface CBpayService {
   public String getDesc(String name) ;
   public List<Student> searchStudentAll();
   public Map<String , String> callPayComing(Map<String , String> map , String apiUrl , String key); //呼叫paycoming api
   public int insertOrUpdateCustomer(CbCustomer cbCustomer);//儲存或更新客戶資料
   public int insertOrUpdateTranDetail(CbTranDetails cbTranDetails);//儲存或更新交易明細資料
   public int insertOrUpdateLog(CbLog cbLog);//儲存或更新交易明細資料
   public List<CbCustomer> searchCustomer(CbCustomer cbCustomer);//搜尋客戶資料
   public List<CbTranDetails> searchTranDetail(CbTranDetails cbTranDetails);//搜尋交易明細資料
}
