package com.CBpayments.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.CBpayments.model.CbCustomer;
import com.CBpayments.model.CbTranDetails;
import com.sun.xml.internal.ws.util.StringUtils;

@Repository
@Transactional(rollbackFor = Exception.class)
@Qualifier("cbPayDao")
public class CBpayDao implements Serializable {

	private static final Logger logger = Logger.getLogger(CBpayDao.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public int insertOrUpdateCustomer(CbCustomer cbCustomer) {

		int reslut = 0;

		try {
			getSession().saveOrUpdate(cbCustomer);
			reslut = 1 ;
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			logger.error("CBpayDao insertOrUpdateCustomer---" + e);
			e.printStackTrace();
		}

		return reslut;

	}

	public int insertOrUpdateTranDetail(CbTranDetails cbTranDetails) {
		// TODO Auto-generated method stub
		int reslut = 0;

		try {
			getSession().saveOrUpdate(cbTranDetails);
			reslut = 1 ;
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			logger.error("CBpayDao insertOrUpdateTranDetail---" + e);
			e.printStackTrace();
		}

		return reslut;
	}

	public List<CbCustomer> searchCustomer(CbCustomer cbCustomer) {
		// TODO Auto-generated method stub
		return getSession().createCriteria(CbCustomer.class).add(Example.create(cbCustomer)).list();
	}

	public List<CbTranDetails> searchTranDetail(CbTranDetails cbTranDetails) {
		String sql = " select * from CBPayment_Tran_Details where 1 = 1 ";
		boolean orderKey = false;
		boolean mchId = false;
		Session session = getSession();
        if(cbTranDetails.getOrderKey() != null && 
        		!cbTranDetails.getOrderKey().isEmpty()) {
        	sql += " and order_key = :orderKey ";
        	orderKey = true;
        }
        if(cbTranDetails.getMchId() != null && 
        		!cbTranDetails.getMchId().isEmpty()) {
        	sql += " and mch_id = :mchId ";
        	mchId = true;
        }
		SQLQuery query = session.createSQLQuery(sql);
		query.addEntity(CbTranDetails.class);
		if(orderKey)query.setParameter("orderKey",cbTranDetails.getOrderKey());
		if(mchId)query.setParameter("mchId",cbTranDetails.getMchId());
		return query.list();
		//return getSession().createCriteria(CbTranDetails.class).add(Example.create(cbTranDetails)).list();
	}

}
