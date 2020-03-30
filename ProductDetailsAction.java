package com.internousdev.galaxy.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.galaxy.dao.ProductInfoDAO;
import com.internousdev.galaxy.dto.ProductInfoDTO;
import com.opensymphony.xwork2.ActionSupport;

public class ProductDetailsAction extends ActionSupport implements SessionAware {

	private int productId;
	private Map<String, Object> session;
	private ProductInfoDTO productInfoDTO = new ProductInfoDTO();
	private List<ProductInfoDTO> categoryList;
	private List<Integer> productCountList;

	//商品を選び、その商品をクリックした場合の処理
	public String execute() {

		// 商品情報を取得
		ProductInfoDAO productInfoDAO = new ProductInfoDAO();
		productInfoDTO = productInfoDAO.getProductInfo(productId);

			if(productInfoDTO.getProductId() == 0) {
				productInfoDTO = null;
			} else {
				// 購入個数のリストを作成
				productCountList = new ArrayList<Integer>();
				for (int i=1; i<=5; i++) {
					productCountList.add(i);
				}
				// 関連商品を探す
				categoryList = productInfoDAO.getCategoryList(productInfoDTO.getCategoryId(), productInfoDTO.getProductId(), 0, 3);
			}
			return SUCCESS;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public List<ProductInfoDTO> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<ProductInfoDTO> categoryList) {
		this.categoryList = categoryList;
	}

	public ProductInfoDTO getProductInfoDTO() {
		return productInfoDTO;
	}

	public void setProductInfoDTO(ProductInfoDTO productInfoDTO) {
		this.productInfoDTO = productInfoDTO;
	}

	public List<Integer> getProductCountList() {
		return productCountList;
	}

	public void setProductCountList(List<Integer> productCountList) {
		this.productCountList = productCountList;
	}

}
