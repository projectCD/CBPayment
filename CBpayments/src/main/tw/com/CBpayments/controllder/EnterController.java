package com.CBpayments.controllder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.CBpayments.model.CbCustomer;
import com.CBpayments.model.CbLog;
import com.CBpayments.model.CbTranDetails;
import com.CBpayments.model.Student;
import com.CBpayments.serviceImp.CBpayServcieImp;
import com.CBpayments.util.ControllerUtil;
import com.CBpayments.wxpay.WXPayConstants;
import com.CBpayments.wxpay.WXPayUtil;

@Controller
@RequestMapping("/")
public class EnterController {

	private final Logger logger = Logger.getLogger(EnterController.class);
	private final CBpayServcieImp cbPayService;

	@Autowired
	public EnterController(CBpayServcieImp cbPayService) {
		this.cbPayService = cbPayService;
	}

	/**
	 * 3.1.2.6.微信扫码接口请求报文(顯示qrCode)
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/genQRCode", method = RequestMethod.POST)
	public String genQRCode(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>(); // 放xml的map
		List tranDetailList = new ArrayList();// 訂單明細
		String errorMsg = ""; // 顯示錯誤訊息
		String url = ""; // qrCode的url
		String key = "";

		// 檢驗參數欄位
		errorMsg = validateStepOne(request);
		if (!errorMsg.isEmpty()) {
			request.setAttribute("errorMsg", errorMsg);
			return ControllerUtil.CONTROLLER_INPUTDATE_PAGE;
		}

		// 找出客戶資料，取得key
		key = this.getKeyByCostomer(request.getParameter("mch_id"));

		// 如果沒有客戶資料，回傳錯誤訊息
		if ("Error".equals(key.substring(0, 5))) {
			errorMsg = "Account is not found";
			request.setAttribute("errorMsg", errorMsg);
			return ControllerUtil.CONTROLLER_INPUTDATE_PAGE;
		}

		// 檢核order_key是否重覆
		CbTranDetails transDetail = new CbTranDetails();
		transDetail.setOrderKey(request.getParameter("order_key"));
		tranDetailList = cbPayService.searchTranDetail(transDetail);
		if (!CollectionUtils.isEmpty(tranDetailList)) {
			errorMsg = "order_key is exist,Please create new order_key.";
			request.setAttribute("errorMsg", errorMsg);
			return ControllerUtil.CONTROLLER_INPUTDATE_PAGE;
		}

		// 組成接口請求map
		map = this.genXmlMap(request);

		// 寫入log
		CbLog cbLog = new CbLog();
		cbLog.setLogMerchants(request.getParameter("mch_id")); // 記錄檔商戶名稱
		try {

			cbLog.setLogContents(WXPayUtil.mapToXml(map)); // 記錄檔內容
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cbLog.setLogTime(Calendar.getInstance()); // 記錄檔時間
		cbPayService.insertOrUpdateLog(cbLog);

		// 呼叫電文 3.1.2.6.微信扫码接口请求报文
		Map<String, String> xmlMap = cbPayService.callPayComing(map, WXPayConstants.WXPAY_URL_3126, key);

		url = xmlMap.get("code_url");// 回傳url

		// url為空值 ，顯示錯誤訊息
		if (url == null || url.length() == 0) {
			errorMsg = xmlMap.get("message") + ",please try again";
		} else {
			// 儲存生成訂單資訊
			CbTranDetails cbTranDetails = new CbTranDetails();
			cbTranDetails.setSequence(WXPayUtil.generateOutNo("CBP"));
			cbTranDetails.setOrderKey(request.getParameter("order_key"));// 商戶訂單主鍵值
			cbTranDetails.setMchId(request.getParameter("mch_id"));// 商戶號碼
			cbTranDetails.setFeeType(request.getParameter("fee_type"));// 幣別
			cbTranDetails.setAmount(Double.valueOf(request.getParameter("total_fee")));// 金額
			cbTranDetails.setOutTradeNo(map.get("out_trade_no"));// 交易訂單號
			cbTranDetails.setCreateDate(Calendar.getInstance());// 建檔日期
			cbTranDetails.setBody(map.get("body"));
			cbPayService.insertOrUpdateTranDetail(cbTranDetails);

		}

		logger.info("url :" + url);
		logger.info("Error Msg :" + errorMsg);
		request.setAttribute("url", url);
		request.setAttribute("errorMsg", errorMsg);

		return ControllerUtil.CONTROLLER_INPUTDATE_PAGE;

	}

	/**
	 * 3.1.2.4.交易状态查询接口请求报文
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/searchTranStatus", method = RequestMethod.POST)
	public String searchTranStatus(HttpServletRequest request) {
		logger.info("searchTranStatus Start");
		List customers = new ArrayList();// 廠商list（找廠商金鑰key）
		List tranDetailList = new ArrayList();// 訂單明細（找出單訂號）
		Map<String, String> map = new HashMap<String, String>(); // 放xml的map
		String msg = ""; // 顯示錯誤訊息
		String key = "";
		String outTradeNo = "";// 交易訂單號
		String xml = "";

		try {
			// 檢驗參數欄位
			msg = validateStepTwo(request);
			if (!msg.isEmpty()) {
				return msg;
			}

			String mchId = request.getParameter("mch_id").toString();// 商戶號
			String orderKey = request.getParameter("order_key").toString();// 廠商記錄訂單號

			// 從db撈取交易訂單號
			CbTranDetails cbTranDetails = new CbTranDetails();
			cbTranDetails.setOrderKey(orderKey);
			cbTranDetails.setMchId(mchId);

			tranDetailList = cbPayService.searchTranDetail(cbTranDetails);
			if (CollectionUtils.isEmpty(tranDetailList)) {
				msg = "orderKey is not found";
				request.setAttribute("errorMsg", msg);
				logger.info("Error Msg :" + msg);
				return msg;
			} else {
				cbTranDetails = (CbTranDetails) tranDetailList.get(0);
				outTradeNo = cbTranDetails.getOutTradeNo();
			}

			// 取得廠商金鑰
			key = this.getKeyByCostomer(mchId);
			if ("Error".equals(key.substring(0, 5))) {
				msg = "Account is not found";
				logger.info("Error Msg :" + msg);
				return msg;
			}

			// XML MAP 組成
			map.put("serviceType", "WXB2C");
			map.put("inputCharset", WXPayConstants.WXPAY_UTF8);
			map.put("interfaceVersion", "1.0");
			map.put("signType", WXPayConstants.MD5);
			map.put("orderNo", outTradeNo);
			map.put("mchtNo", mchId);

			// 呼叫電文 3.1.2.6.微信扫码接口请求报文
			Map<String, String> xmlMap = cbPayService.callPayComing(map, WXPayConstants.WXPAY_URL_3124, key);

			// update 訂單資訊 及 訊息
			cbTranDetails.setMessage(xmlMap.get("message"));
			cbTranDetails.setStatus(xmlMap.get("status"));
			cbTranDetails.setEditDate(Calendar.getInstance());
			if ("0".equals(xmlMap.get("status"))) {
				String payStatus = xmlMap.get("payStatus");
				cbTranDetails.setPayStatus(payStatus);
				msg = "trade query success.";
				if ("00000".equals(payStatus)) {
					msg = msg + "payment success";
				} else if ("NOTPAY".equals(payStatus)) {
					msg = msg + "payment cancel";
				} else if ("Z0003".equals(payStatus)) {
					msg = msg + "payment failed";
				}
			} else {
				msg = "trade query failed.";
			}

			cbPayService.insertOrUpdateTranDetail(cbTranDetails);

			xml = WXPayUtil.mapToXml(xmlMap);
			request.setAttribute("xml：", xml);
			logger.info("XML :" + xml);
			return xml;
		} catch (Exception e) {
			e.printStackTrace();
			xml = "Search System has problem.";
		}
		request.setAttribute("errorMsg", msg);
		logger.info("Msg :" + msg);
		return xml;
	}

	/**
	 * 500錯誤
	 * 
	 * @param request
	 * @return
	 */
	// @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	// @ExceptionHandler(Exception.class)
	// public void notFoundHandler() {
	// logger.debug("Item not found. HTTP 500 returned.");
	// }

	/**
	 * 組成接口請求map
	 * 
	 * @param request
	 * @return
	 */
	private Map<String, String> genXmlMap(HttpServletRequest request) {

		String mch_id = request.getParameter("mch_id"); // 頁面參數 商戶號
		String body = request.getParameter("body"); // 頁面參數 商品描述
		String total_fee = request.getParameter("total_fee"); // 頁面參數 金額
		if (isInteger(total_fee)) {
			// 為美金轉人民幣
			Integer totalFee = (int) (Double.valueOf(total_fee) * 100);
			total_fee = String.valueOf(totalFee);
		}
		String fee_type = request.getParameter("fee_type"); // 頁面參數 幣別
		Map<String, String> map = new HashMap<String, String>();

		logger.info("bodybodybodybodybodybody-----" + body);

		map.put("service", WXPayConstants.WXPAY_SERVICE);
		map.put("version", "1.0");
		map.put("charset", WXPayConstants.WXPAY_UTF8);
		map.put("sign_type", WXPayConstants.MD5);
		map.put("mch_id", mch_id);
		map.put("out_trade_no", WXPayUtil.generateOutNo());
		map.put("body", body);
		map.put("fee_type", fee_type);
		// map.put("fee_type", WXPayConstants.WXPAY_CNY);
		map.put("total_fee", total_fee);
		map.put("mch_create_ip", "192.168.2.190");
		map.put("notify_url", "http//wx.unitepay.com.cn/onlinepay/sdjNotice");
		map.put("callback_url", "http//wx.unitepay.com.cn/onlinepay/success_pay.jsp");
		map.put("nonce_str", WXPayUtil.generateUUID());

		return map;
	}

	/**
	 * 測試網頁
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/secretPage", method = RequestMethod.GET)
	public String secretPage(HttpServletRequest request) {
		return ControllerUtil.CONTROLLER_TEST_PAGE;

	}

	@ResponseBody
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public List<Student> getSearchResultViaAjax(HttpServletRequest request) {
		List list = null;
		System.out.println("ajax !!");

		return list;

	}

	@RequestMapping(method = RequestMethod.GET)
	public String sayHello(ModelMap model) {
		model.addAttribute("greeting", "Hello World from Spring 4 MVC");
		System.out.println("start welcome");

		return ControllerUtil.CONTROLLER_WELCOME_PAGE;
	}

	@RequestMapping(value = "/helloagain", method = RequestMethod.GET)
	public String sayHelloAgain(HttpServletRequest request) {
		String name = request.getParameter("name");

		name = cbPayService.getDesc(name);

		request.setAttribute("name", name);

		System.out.println("name: " + name);

		return ControllerUtil.CONTROLLER_INPUTDATE_PAGE;
	}

	@RequestMapping(value = "/goGoogle", method = RequestMethod.GET)
	public String goGoogle(HttpServletRequest request) {

		System.out.println("google");

		String redirectUrl = request.getScheme() + "://www.google.com.tw";
		return "redirect:" + redirectUrl;

	}

	/**
	 * 透過客戶account 取得客戶金鑰 key
	 * 
	 * @param mchId
	 * @return
	 */
	private String getKeyByCostomer(String mchId) {
		List list = new ArrayList(); // 客戶資料list
		String key = "";

		CbCustomer cbCustomer = new CbCustomer();
		cbCustomer.setMchId(mchId);
		cbCustomer.setCurrentStatus("Y");
		list = cbPayService.searchCustomer(cbCustomer);

		// 如果沒有客戶資料，回傳錯誤訊息
		if (CollectionUtils.isEmpty(list)) {
			key = "Error.Account is not found";
		} else {
			cbCustomer = (CbCustomer) list.get(0);
			key = cbCustomer.getMchKey();
		}
		return key;
	}

	/**
	 * 檢核 數字
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isInteger(String value) {
		Pattern pattern = Pattern.compile("^([-+]?\\d+)(\\.\\d+)?$");
		return pattern.matcher(value).matches();
	}

	/**
	 * 檢核 request 參數
	 * 
	 * @param request
	 * @return
	 */
	public String validateStepOne(HttpServletRequest request) {
		String msg = "";
		// 頁面參數 商戶號
		if (request.getParameter("mch_id") == null || request.getParameter("mch_id").isEmpty()) {
			msg += " mch_id is Empty. ";
		}
		// 頁面參數 商品描述
		if (request.getParameter("body") == null || request.getParameter("body").isEmpty()) {
			msg += " body is Empty. ";
		}
		// 頁面參數 幣別
		if (request.getParameter("fee_type") == null || request.getParameter("fee_type").isEmpty()) {
			msg += " fee_type is Empty. ";
		}
		// 頁面參數 order_key
		if (request.getParameter("order_key") == null || request.getParameter("order_key").isEmpty()) {
			msg += " order_key is Empty. ";
		}
		// 頁面參數 金額
		if (request.getParameter("total_fee") == null || request.getParameter("total_fee").isEmpty()) {
			msg += " total_fee is Empty. ";
		} else {
			if (isInteger(request.getParameter("total_fee"))) {
				if (Double.valueOf(request.getParameter("total_fee")) == 0) {
					msg += " total_fee can't be zero. ";
				}
			} else {
				msg += " total_fee must be a number. ";
			}
		}
		return msg;
	}

	/**
	 * 檢核 request 參數
	 * 
	 * @param request
	 * @return
	 */
	public String validateStepTwo(HttpServletRequest request) {
		String msg = "";
		// 頁面參數 商戶號
		if (request.getParameter("mch_id") == null || request.getParameter("mch_id").isEmpty()) {
			msg += " mch_id is Empty. ";
		}
		// 頁面參數 order_key
		if (request.getParameter("order_key") == null || request.getParameter("order_key").isEmpty()) {
			msg += " order_key is Empty. ";
		}
		return msg;
	}
}