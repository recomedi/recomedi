<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ include file="/WEB-INF/header.jsp" %>
 
 <link href="./style.css" rel="stylesheet">
	<div class="wrapper">
		<section class="prescription">			
			<div class="btnBox right">
				<button class="btn">업데이트</button>
			</div>
					<table class="listTable listTable2 center">
						<thead>
							<tr>
								<th>번호</th>
								<th>조제일자</th>
								<th>처방기관</th>
								<th>조제기관</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="prescription" items="${prescriptions}">
							<tr onclick="window.location.href='${pageContext.request.contextPath}/medicine/prescriptionDetail.do?pidx=${prescription.pidx}'">
								<td>${prescription.pidx}</td>
								<td>${prescription.resMenufactureDate}</td>
								<td>${prescription.resPrescribeOrg}</td>
								<td>${prescription.commBrandName}</td>
							</tr>
							</c:forEach>
						
							<!-- 데이터가 없는 경우 -->
							<c:if test="${empty prescriptions}">
							    <tr>
							        <td colspan="4">조회된 처방 정보가 없습니다.</td>
							    </tr>
							</c:if>
						</tbody>
						</table>

				
				<p class="infoText">※조제일자 또는 조제기관을 클릭하시면 조제내역을 확인하실 수 있습니다.</p>

			<div class="page">
				<ul class="flex">
					<li class="center"><a href="#">◀</a></li>
					<li class="center on"><a href="#">1</a></li>
					<li class="center"><a href="#">2</a></li>
					<li class="center"><a href="#">3</a></li>
					<li class="center"><a href="#">4</a></li>
					<li class="center"><a href="#">5</a></li>
					<li class="center"><a href="#">6</a></li>
					<li class="center"><a href="#">7</a></li>
					<li class="center"><a href="#">8</a></li>
					<li class="center"><a href="#">9</a></li>
					<li class="center"><a href="#">10</a></li>
					<li class="center"><a href="#">▶</a></li>
				</ul>
			</div>
		</div>
	
<%@ include file="/WEB-INF/footer.jsp" %>