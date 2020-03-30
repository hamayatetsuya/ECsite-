<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="./css/galaxy.css">
<link rel="stylesheet" type="text/css" href="./css/header.css">
<link rel="stylesheet" type="text/css" href="./css/productDetails.css">

<title>商品詳細画面</title>
</head>
<body>

	<jsp:include page="header.jsp" />
	<div id="contents">
		<h1>商品詳細</h1>

		<s:if test="productInfoDTO != null">
			<s:form action="AddCartAction">
			<div class="2column-contents">

				<%--表示する商品情報の画像 --%>
				<div class="left">
					<img
						src='<s:property value="productInfoDTO.imageFilePath" />/<s:property value="productInfoDTO.imageFileName" />'
						class="item-image-box-320" /> <br>
				</div>

				<%--表示する商品情報 --%>
				<div id="right">
					<table class="list-table">
						<tr>
							<th scope="row"><s:label value="商品名" /></th>
							<td><s:property value="productInfoDTO.productName" /></td>
						</tr>

						<tr>
							<th scope="row"><s:label value="商品名ふりがな" /></th>
							<td><s:property value="productInfoDTO.productNameKana" /></td>
						</tr>

						<tr>
							<th scope="row"><s:label value="値段" /></th>
							<td><s:property value="productInfoDTO.price" />円</td>
						</tr>

						<tr>
							<th scope="row"><s:label value="購入個数" /></th>
							<td><s:select name="productCount" list="%{productCountList}" />個</td>
						</tr>

						<tr>
							<th scope="row"><s:label value="発売会社名" /></th>
							<td><s:property value="productInfoDTO.releaseCompany" /></td>
						</tr>

						<tr>
							<th scope="row"><s:label value="発売年月日" /></th>
							<td><s:property value="productInfoDTO.releaseDate" /></td>
						</tr>

						<tr>
							<th scope="row"><s:label value="商品詳細情報" /></th>
							<td><s:property value="productInfoDTO.productDescription" /></td>
						</tr>
					</table>
				</div>

				<s:hidden name="productId" value="%{productInfoDTO.productId}" />

				<div class="btn-box">
					<s:submit value="カートに追加" class="submit" />
				</div>
			</div>
			</s:form>

			<s:if test="categoryList!= null && categoryList.size()>0">
				<div id="box">
					<div class="recommend-box-list">
					<h2>【関連商品】</h2>
					<s:iterator value="categoryList">
						<div class="recommend-box">
							<a
								href='<s:url action="ProductDetailsAction">
								<s:param name="productId" value="%{productId}" />
								</s:url>'><img
								src='<s:property value="imageFilePath" />/<s:property value="imageFileName" />'
								class="item-image-box-100" />
								<s:property value="productName" /><br>
							</a>
						</div>
					</s:iterator>
					</div>
				</div>
			</s:if>

		</s:if>

		<s:else>
			<div class="info">
				商品の詳細情報がありません。
			</div>
		</s:else>
	</div>

</body>
</html>