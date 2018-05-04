package com.CBpayments.serviceImp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CBpayments.dao.CBpayDao;
import com.CBpayments.dao.StudnetDao;
import com.CBpayments.model.CbLog;
import com.CBpayments.model.CbCustomer;
import com.CBpayments.model.CbTranDetails;
import com.CBpayments.model.Student;
import com.CBpayments.service.CBpayService;
import com.CBpayments.wxpay.WXPayConstants;
import com.CBpayments.wxpay.WXPayUtil;

@Service
public class CBpayServcieImp implements CBpayService {

	@Autowired
	private StudnetDao studnetDao;
	
	@Autowired
	private CBpayDao cbPayDao;

	public CBpayDao getCbPayDao() {
		return cbPayDao;
	}

	public void setCbPayDao(CBpayDao cbPayDao) {
		this.cbPayDao = cbPayDao;
	}

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

	@Override
	public Map<String, String> callPayComing(Map<String, String> map , String apiUrl , String key) {
		// TODO Auto-generated method stub

		String sing ;  //簽名
		String xml;   //產出的xml格式
		Map<String, String> realut =  new HashMap<>();//xml回應電文的map檔
		
		try {
			
		//取得簽名	
		sing = WXPayUtil.generateSignature(map, key);
		//放入map
		map.put(WXPayConstants.FIELD_SIGN, sing);
		System.out.println("=======================================");
		
		//產生xml格式
		xml = WXPayUtil.mapToXml(map);
		URL httpUrl = new URL(apiUrl);
		HttpURLConnection httpURLConnection = (HttpURLConnection) httpUrl.openConnection();
		// httpURLConnection.setRequestProperty("Host", "api.mch.weixin.qq.com");
		httpURLConnection.setDoOutput(true);
		httpURLConnection.setRequestMethod("POST");
		httpURLConnection.setConnectTimeout(10 * 1000);
		httpURLConnection.setReadTimeout(10 * 1000);
		httpURLConnection.connect();
		OutputStream outputStream = httpURLConnection.getOutputStream();
		outputStream.write(xml.getBytes(WXPayConstants.WXPAY_UTF8));

		InputStream inputStream = httpURLConnection.getInputStream();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, WXPayConstants.WXPAY_UTF8));
		final StringBuffer stringBuffer = new StringBuffer();
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			stringBuffer.append(line);
		}
		String resp = stringBuffer.toString();
		if (stringBuffer != null) {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (inputStream != null) {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (outputStream != null) {
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		realut = WXPayUtil.xmlToMap(resp);
		
		
	}catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	return realut;
}

	@Override
	public int insertOrUpdateCustomer(CbCustomer cbCustomer) {
		// TODO Auto-generated method stub
		return getCbPayDao().insertOrUpdateCustomer(cbCustomer);
	}

	@Override
	public int insertOrUpdateTranDetail(CbTranDetails cbTranDetails) {
		// TODO Auto-generated method stub
		return getCbPayDao().insertOrUpdateTranDetail(cbTranDetails);
	}

	@Override
	public List<CbCustomer> searchCustomer(CbCustomer cbCustomer) {
		// TODO Auto-generated method stub
		return getCbPayDao().searchCustomer(cbCustomer);
	}

	@Override
	public List<CbTranDetails> searchTranDetail(CbTranDetails cbTranDetails) {
		// TODO Auto-generated method stub
		return getCbPayDao().searchTranDetail(cbTranDetails);
	}

	@Override
	public int insertOrUpdateLog(CbLog cbLog) {
		// TODO Auto-generated method stub
		return getCbPayDao().insertOrUpdateLog(cbLog);
	}

}
