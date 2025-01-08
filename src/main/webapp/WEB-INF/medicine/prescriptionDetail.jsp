<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>처방받은약 상세</title>
</head>
<body>
		<h2>처방 상세 정보</h2>
		
		<!-- 사용자 정보 -->
		<table class="table">
		    <tr>
		        <th>조제일자</th>
		        <td>${prescription.resManufactureDate}</td>
		    </tr>
		    <tr>
		        <th>처방기관</th>
		        <td>${prescription.resPrescribeOrg}</td>
		    </tr>
		    <tr>
		        <th>조제기관</th>
		        <td>${prescription.commBrandName}</td>
		    </tr>
		</table>
		
		<!-- 약물 정보 -->
		<h3>약물 정보</h3>
		
		<c:forEach var="drug" items="${prescription.drugs}">
    <tr>
        <td>${drug.resDrugName}</td>
        <td>${drug.resIngredients}</td>
        <td>${drug.resPrescribeDrugEffect}</td>
         <td>${drug.resPrescribeDrugEffect}</td>
    
    </tr>
</c:forEach>

		
		<c:if test="${empty prescription.resDrugList}">
		    <p>조회된 약물 정보가 없습니다.</p>
		</c:if>
		
		<!-- 버튼 -->
		<div class="button-group">
		    <!-- 목록으로 돌아가기 버튼 -->
		    <button onclick="window.location.href='${pageContext.request.contextPath}/medicine/prescriptionList.do'">목록으로 돌아가기</button>
		</div>

</body>
</html>