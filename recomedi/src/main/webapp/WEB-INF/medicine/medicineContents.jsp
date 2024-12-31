<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.recomedi.myapp.domain.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글내용</title>
<link href="./style.css" rel="stylesheet">
</head>
<body>
	<div class="fixed sideBar">
		<nav class="flex relative">
			<ul class="sideMenu">
				<li><a href="#"><img src="./image/login.png" alt="로그인">로그인</a></li>
				<li><a href="#"><img src="./image/join.png" alt="회원가입"></a>회원가입</li>
				<li><a href="#"><img src="./image/editProfile.png" alt="정보수정">정보수정</a></li>
				<li><a href="#"><img src="./image/prescription.png" alt="처방받은약">처방받은약</a></li>
				<li><a href="#"><img src="./image/scrap_blank.png" alt="스크랩">스크랩</a></li>
				<li><a href="#"><img src="./image/alarm.png" alt="내가문의한글">내가문의한글</a></li>
				<li><a href="#"><img src="./image/medicine.png" alt="전체의약품">전체의약품조회</a></li>
				<li><a href="#"><img src="./image/notice.png" alt="공지사항">공지사항</a></li>
				<li><a href="#"><img src="./image/inquiry.png" alt="문의게시판">문의게시판</a></li>
				<li class="logout"><a href="#"><img src="./image/logout.png" alt="로그아웃">로그아웃</a></li>
			</ul>
		</nav>
		<button class="close absolute"><img src="./image/menuClose.svg" alt="menuClose"></button>
	</div>
	
	<header class="relative center" id="hd">
		<a href="#" class="absolute logo"><img src="./image/logo.png" alt="logo"></a>
		<h2 class="mainTitle relative bold">약품정보</h2>
		<div class="absolute menu flex">
			<a href="#" class="relative alarm confirmation"><img src="./image/alarm.png" alt="alarm"></a>
			<button class="btnMenu flex"><p class="first"></p><p class="second"></p><p class="third"></p></button>
		</div>
	</header>

	<div class="wrapper">
		<section class="shadow detailContents">
			<h2 class="contentTitle center shadow regular">프로타제정(수출명:DAESHINPROTASE)</h2>
			<p class="hashtag bold">#감기약</p>
			<div class="content shadow">
				1.다음과 같은 사람은 이 약을 복용하지 말 것.
				이 약은 유당을 함유하고 있으므로, 갈락토오스 불내성(galactose intolerance), Lapp 유당분해효소 결핍증(Lapp lactase deficiency) 또는 포도당-갈락토오스 흡수장애(glucose-galactose malabsorption) 등의 유전적인 문제가 있는 환자에게는 투여하면 안 된다.
				
				2.다음과 같은 사람은 이 약을 복용하기 전에 의사, 치과의사, 약사와 상의 할 것.
				1) 혈액응고이상 환자
				2) 심한 간장애 또는 신장애(신장(콩팥)장애) 환자
				3) 임부 및 임신하고 있을 가능성이 있는 여성(임신중 투여에 관한 안전성이 확립되어 있지 않으므로 치료상의 유익성이 위험성을 상회(웃돎)한다고 판단되는 경우에만 투여한다.)
				4) 항응고제를 투여하고 있는 환자(병용(함께 복용(사용))투여에 의해, 항응고제의 작용이 증강될 수 있으므로 관찰을 충분히 하고 신중히 투여한다.
				
				3.다음과 같은 경우 이 약의 복용을 즉각 중지하고 의사, 치과의사, 약사와 상의할 것. 상담 시 가능한 한 이 첨부문서를 소지할 것.
				1) 과민반응 : 발진, 발적(충혈되어 붉어짐) 등이 나타날 수 있으므로 이러한 증상이 나타나는 경우에는 투여를 중지하고 적절한 처치를 한다.
				2) 소화기계 : 설사, 변비, 때때로 식욕부진, 배가 거북함, 구역, 구토 등의 증상이 나타날 수 있다.
				3) 혈액계 : 드물게 코피, 혈액가래 등의 출혈 경향이 나타날 수 있다.
				
				4.기타 이 약의 복용 시 주의할 사항
				정해진 용법·용량을 잘 지킬 것.
				
				5.다음 환자에는 신중히 투여할 것				
				이 약은 황색4호(타르트라진)를 함유하고 있으므로 이 성분에 과민하거나 알레르기 병력이 있는 환자에는 신중히 투여한다.
				
				6.저장상의 주의사항
			</div>
		</section>

		<div class="page contentPage">
			<ul class="flex">
				<li><a href="#">◀</a></li>
				<li><a href="#" class="btn">목록가기</a></li>
				<li><a href="#">▶</a></li>
			</ul>
		</div>
	</div>

	<footer id="ft">
		<ul class="madeBy flex">
			<li>Recomedi <a href="https://github.com/recomedi/recomedi.git" target="_blank">Github</a></li>
			<li>민들레 <a href="https://velog.io/@ktiun9630" target="_blank">velog</a> | <a href="https://github.com/Chan01116" target="_blank">Github</a></li>
			<li>데프콘 <a href="https://velog.io/@rivae_108" target="_blank">velog</a> | <a href="https://github.com/Rivea108" target="_blank">Github</a></li>
			<li>지렁이 <a href="https://velog.io/@biso15" target="_blank">velog</a> | <a href="https://github.com/biso15" target="_blank">Github</a></li>
			<li>구리 <a href="https://velog.io/@guri670" target="_blank">velog</a> | <a href="https://github.com/guri670" target="_blank">Github</a></li>
		</ul>
		<p class="copyright">Copyright 2024. Recomedi. All Rights reserved.</p>
	</footer>
</body>
</html>