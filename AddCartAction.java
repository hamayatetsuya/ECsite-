package com.internousdev.galaxy.action;

import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.galaxy.dao.CartInfoDAO;
import com.internousdev.galaxy.dto.CartInfoDTO;
import com.opensymphony.xwork2.ActionSupport;

public class AddCartAction extends ActionSupport implements SessionAware {

	private int productId;
	private int productCount;
	private int totalPrice;
	private List<CartInfoDTO> cartInfoDTOList;
	private Map<String, Object> session;

	public String execute() {
		if(!session.containsKey("tempUserId") && !session.containsKey("userId")) {
			return "sessionTimeout";
		}

		String result=ERROR;
		String userId = null;

		String tempLogined = String.valueOf(session.get("loginFlg"));
		int loginFlg = "null".equals(tempLogined)? 0 : Integer.parseInt(tempLogined);
		if(loginFlg == 1) {
			userId = session.get("userId").toString();
		} else {
			userId = String.valueOf(session.get("tempUserId"));
		}

		// カートに商品を新規登録or情報を更新する
		CartInfoDAO cartInfoDAO = new CartInfoDAO();
		int count = 0;
		// 追加しようとしている商品と同じ商品のデータがすでにDBに存在するかをチェックする。
		if(cartInfoDAO.searchProductId(userId, productId)){
			//存在する場合は、商品の個数を更新する。
			count = cartInfoDAO.updateProductCount(userId, productId, productCount);
		}else{
			//存在しない場合は、新規登録を行う。
			count = cartInfoDAO.insertCartInfo(userId, productId, productCount);
		}

		if(count > 0) {
			cartInfoDTOList = cartInfoDAO.getCartList(userId);
			totalPrice = cartInfoDAO.getTotalPrice(userId);
			result=SUCCESS;
		}
		return result;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getProductCount() {
		return productCount;
	}
	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	public List<CartInfoDTO> getCartInfoDTOList() {
		return cartInfoDTOList;
	}
	public void setCartInfoDTOList(List<CartInfoDTO> cartInfoDTOList) {
		this.cartInfoDTOList = cartInfoDTOList;
	}
	public Map<String, Object> getSession() {
		return session;
	}
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}
