package com.internousdev.galaxy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.internousdev.galaxy.dto.ProductInfoDTO;
import com.internousdev.galaxy.util.DBConnector;

public class ProductInfoDAO {

	//puroductInfoから紐づいている商品データの取得
		public ProductInfoDTO getProductInfo(int productId) {

			DBConnector db = new DBConnector();
			Connection con = db.getConnection();
			ProductInfoDTO productInfoDTO = new ProductInfoDTO();

			//product_infoテーブルから商品IDに紐づくデータの取得
			String sql = "SELECT * FROM product_info WHERE product_id = ?";

			try {
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, productId);
				ResultSet rs = ps.executeQuery();

				//DBから取得した値をproductInfoDTOに格納
				while (rs.next()) {
					productInfoDTO.setId(rs.getInt("id"));
					productInfoDTO.setProductId(rs.getInt("product_id"));
					productInfoDTO.setPrice(rs.getInt("price"));
					productInfoDTO.setCategoryId(rs.getInt("category_id"));
					productInfoDTO.setProductName(rs.getString("product_name"));
					productInfoDTO.setProductNameKana(rs.getString("product_name_kana"));
					productInfoDTO.setProductDescription(rs.getString("product_description"));
					productInfoDTO.setImageFileName(rs.getString("image_file_name"));
					productInfoDTO.setImageFilePath(rs.getString("image_file_path"));
					productInfoDTO.setReleaseCompany(rs.getString("release_company"));
					productInfoDTO.setReleaseDate(rs.getDate("release_date"));

				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return productInfoDTO;

		}

		//関連商品の情報の取得
		public List<ProductInfoDTO> getCategoryList(int categoryId,int productId,int limitOffset,int limitRowCount) {

			DBConnector db = new DBConnector();
			Connection con = db.getConnection();
			List<ProductInfoDTO> categoryList = new ArrayList<ProductInfoDTO>();

			String sql ="SELECT * FROM product_info WHERE category_id = ? AND product_id not in(?) ORDER BY rand() limit ?,?";

			try {
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, categoryId);
				ps.setInt(2, productId);
				ps.setInt(3, limitOffset);
				ps.setInt(4, limitRowCount);

				ResultSet rs = ps.executeQuery();

				while(rs.next()) {
					ProductInfoDTO productDTO = new ProductInfoDTO();

					productDTO.setId(rs.getInt("id"));
					productDTO.setProductId(rs.getInt("product_id"));
					productDTO.setProductName(rs.getString("product_name"));
					productDTO.setProductNameKana(rs.getString("product_name_kana"));
					productDTO.setProductDescription(rs.getString("product_description"));
					productDTO.setCategoryId(rs.getInt("category_id"));
					productDTO.setPrice(rs.getInt("price"));
					productDTO.setImageFilePath(rs.getString("image_file_path"));
					productDTO.setImageFileName(rs.getString("image_file_name"));
					productDTO.setReleaseDate(rs.getDate("release_date"));
					productDTO.setReleaseCompany(rs.getString("release_company"));
					categoryList.add(productDTO);
				}
			} catch (SQLException e) {
				e.printStackTrace();

			} finally {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}

			return categoryList;
		}

//		検索条件が categoryId == 1 (全てのカテゴリー) の場合に使用する
		public List<ProductInfoDTO> getFromAllProductInfo(List<String> searchWordList) {

			DBConnector db = new DBConnector();
			Connection con = db.getConnection();

			List<ProductInfoDTO> productInfoList = new ArrayList<ProductInfoDTO>();

			String sql = "SELECT * FROM product_info";

//			検索ワードが指定されている場合のみ、for文を実行する
			if(!"".equals(searchWordList.get(0))) {

				for (int i = 0; i < searchWordList.size(); i++) {
					if (i == 0) {
						sql += " WHERE (product_name LIKE '%" + searchWordList.get(i) + "%' OR product_name_kana LIKE '%" + searchWordList.get(i) + "%')";
					} else {
						sql += " OR (product_name LIKE '%" + searchWordList.get(i) + "%' OR product_name_kana LIKE'%" + searchWordList.get(i) + "%')";
					}
				}
			}

			sql += " ORDER BY product_id ASC";

			try {
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);

				while(rs.next()) {
					ProductInfoDTO dto = new ProductInfoDTO();
					dto.setProductId(rs.getInt("product_id"));
					dto.setProductName(rs.getString("product_name"));
					dto.setProductNameKana(rs.getString("product_name_kana"));
					dto.setImageFilePath(rs.getString("image_file_path"));
					dto.setImageFileName(rs.getString("image_file_name"));
					dto.setPrice(rs.getInt("price"));
					productInfoList.add(dto);
				}

			} catch (SQLException e) {
				e.printStackTrace();

			} finally {

				try {
					con.close();
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}

			return productInfoList;

		}

//		検索条件が categoryId != 1 の場合に使用する
		public List<ProductInfoDTO> getProductInfo(int categoryId, List<String> searchWordList) {

			DBConnector db = new DBConnector();
			Connection con = db.getConnection();

			List<ProductInfoDTO> productInfoList = new ArrayList<ProductInfoDTO>();

			String sql = "SELECT * FROM product_info WHERE category_id = ?";

//			検索ワードが指定されている場合のみ、for文を実行する
			if(!"".equals(searchWordList.get(0))) {

				for (int i = 0; i < searchWordList.size(); i++) {
					if (i == 0) {
						sql += " AND ((product_name LIKE '%" + searchWordList.get(i) + "%' OR product_name_kana LIKE '%" + searchWordList.get(i) + "%')";
					} else {
						sql += " OR (product_name LIKE '%" + searchWordList.get(i) + "%' OR product_name_kana LIKE '%" + searchWordList.get(i) + "%')";
					}
				}
				sql += ")";
			}

			sql += " ORDER BY product_id ASC";

			try {
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, categoryId);
				ResultSet rs = ps.executeQuery();

				while(rs.next()) {
					ProductInfoDTO dto = new ProductInfoDTO();
					dto.setProductId(rs.getInt("product_id"));
					dto.setProductName(rs.getString("product_name"));
					dto.setProductNameKana(rs.getString("product_name_kana"));
					dto.setImageFilePath(rs.getString("image_file_path"));
					dto.setImageFileName(rs.getString("image_file_name"));
					dto.setPrice(rs.getInt("price"));
					productInfoList.add(dto);
				}

			} catch (SQLException e) {
				e.printStackTrace();

			} finally {

				try {
					con.close();
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}

			return productInfoList;

		}

}