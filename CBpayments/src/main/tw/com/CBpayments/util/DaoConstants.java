package com.CBpayments.util;

public class DaoConstants {
	
	//select 
	public final static String SELECT_CUSTOMER_LIST_ALL = "SELECT * FROM CBPayment_Customer_List";
	public final static String SELECT_TRAND_DETAILS_ALL = "SELECT * FROM CBPayment_Tran_Details";
	
	//insert 
	public final static String INSERT_CUSTOMER_LIST = "insert into CBPayment_Customer_List (mch_id,mch_name,current_status,mch_key,reg_date,create_date,edit_date) values(?,?,?,?,?,?,?)";
	public final static String INSERT_TRAND_DETAILS = "insert into CBPayment_Tran_Details ((order_key,mch_id,out_trade_no,pay_status,fee_type,amount,status,message,pay_time,create_date,edit_date)) values(?,?,?,?,?,?,?,?,?,?,?)";
}
