<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import ="java.util.*" %>
<%@ taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글목록</title>
<link href="${pageContext.request.contextPath}/resources/css/style2.css" rel="stylesheet">
</head>
<body>
<header>
	<h2 class="mainTitle">글목록</h2>
	
<form class="search" name="frm" action="${pageContext.request.contextPath}/notice/noticeList.aws" method="get">
  	<select name="searchType">
	    <option value="subject">성분명</option>
   		 <option value="writer">제품명</option>
  	</select>
  	<input type="text" name="keyword">
  	<button type="submit" class="btn"><!-- 색상변경 -->검색</button>
</form>
</header>

<section>	
	<table class="listTable">
		<tr>
			<th>번호</th>
			<th>제목</th>
			<th>조회수</th>
			<th>등록일</th>
		</tr>
		<tr>
			<td>170</td>
			<td class="title"><a href="./detail.html" >장애학생들을 위한 특별한 피아노</a></td>
			<td>5</td>
			<td>2024-10-12</td>
		</tr>
		<tr>
			<td>170</td>
			<td class="title"><a href="./detail.html" >장애학생들을 위한 특별한 피아노</a></td>
			<td>5</td>
			<td>2024-10-12</td>
		</tr>
		<tr>
			<td>170</td>
			<td class="title"><a href="./detail.html">장애학생들을 위한 특별한 피아노</a></td>
			<td>5</td>
			<td>2024-10-12</td>
		</tr>
		<tr>
			<td>170</td>
			<td class="title"><a href="./detail.html">장애학생들을 위한 특별한 피아노</a></td>
			<td>5</td>
			<td>2024-10-12</td>
		</tr>
		<tr>
			<td>170</td>
			<td class="title"><a href="./detail.html">장애학생들을 위한 특별한 피아노</a></td>
			<td>5</td>
			<td>2024-10-12</td>
		</tr>
		<tr>
			<td>170</td>
			<td class="title"><a href="./detail.html">장애학생들을 위한 특별한 피아노</a></td>
			<td>5</td>
			<td>2024-10-12</td>
		</tr>
		<tr>
			<td>170</td>
			<td class="title"><a href="./detail.html">장애학생들을 위한 특별한 피아노</a></td>
			<td>5</td>
			<td>2024-10-12</td>
		</tr>
		<tr>
			<td>170</td>
			<td class="title"><a href="./detail.html">장애학생들을 위한 특별한 피아노</a></td>
			<td>5</td>
			<td>2024-10-12</td>
		</tr>
		<tr>
			<td>170</td>
			<td class="title"><a href="./detail.html">장애학생들을 위한 특별한 피아노</a></td>
			<td>5</td>
			<td>2024-10-12</td>
		</tr>
	<tr>
			<td>170</td>
			<td class="title"><a href="./detail.html">장애학생들을 위한 특별한 피아노</a></td>
			<td>5</td>
			<td>2024-10-12</td>
		</tr>
	</table>
	
	<div class="btnBox">
		<a class="btn aBtn" href="${pageContext.request.contextPath}/notice/noticeWrite.aws">글쓰기</a>
	</div>
	
	<div class="page">
		<ul>
			<li class="on">1</li>
			<li>2</li>
			<li>3</li>
			<li>4</li>
			<li>5</li>
			<li>6</li>
			<li>7</li>
			<li>8</li>
			<li>9</li>
			<li>10</li>
		</ul>
	</div>
</section>

</body>
</html>