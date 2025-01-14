<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

   <%@ include file="/WEB-INF/header.jsp" %>
    
  <script>
  
  const email = /[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[0-9a-zA-Z]$/i;
  const phone = /^\d{1,15}$/;  // 9 ~ 11자의 숫자만 사용
  
  function check() {
	  
	  // 유효성 검사하기
	  let fm = document.frm;

	  if (fm.pwd.value == "") {
		  alert("비밀번호를 입력해주세요");
		  fm.pwd.focus();
		  return;
	  } else if (fm.RecheckPassword.value == "") {
		  alert("비밀번호 확인을 입력해주세요");
		  fm.RecheckPassword.focus();
		  return;		  
	  } else if(fm.pwd.value != fm.RecheckPassword.value) {
		  alert("비밀번호가 다릅니다.");
		  fm.pwd.value = "";
		  fm.RecheckPassword.value = "";
		  return;
	  } if (fm.nickname.value == "") {
		  alert("닉네임을 입력해주세요");
		  fm.nickname.focus();
		  return;
	  } else if (fm.phone.value == "") {
		  alert("연락처를 입력해주세요");
		  fm.phone.focus();
		  return;
	  } else if (phone.test(fm.phone.value) == false) {
		  alert("연락처는 숫자만 입력해 주세요");
		  fm.membermail.value = "";
		  fm.membermail.focus();
		  return;
	  }
	  
	  	  
	  let ans = confirm("수정하시겠습니까?");	  
	  if (ans == true) {
		  fm.action="${pageContext.request.contextPath}/member/memberMypageAction.do";
		  fm.method="post";
		  fm.submit();
	  }
	  
	  return;
  }
  
  function cancel() {
	  
	  let ans = confirm("탈퇴하시겠습니까?");	  
	  if (ans == true) {
		  location.href = "${pageContext.request.contextPath}/member/memberDeleteAction.do";
	  }
	  
	  return;
  }
  
  </script>
    
    <div class="wrapper flex">
		<section class="member shadow">
			<img src="${pageContext.request.contextPath}/resources/images/bg_modify.png" alt="정보수정" class="bg block">
			<h2 class="regular title center">정보수정</h2>
			<form name="frm">
				<label for="password" class="block">비밀번호</label>
				<input type="password" id="password" placeholder="password" name="pwd">
				<label for="RecheckPassword" class="block">비밀번호확인</label>
				<input type="password" id="RecheckPassword" placeholder="Recheck password">
				<label for="nickname" class="block">닉네임</label>
				<input type="text" id="nickname" placeholder="Nick name" value="${requestScope.mv.nickname}" name="nickname">
				<label for="phone" class="block">연락처</label>
				<input type="text" id="phone" placeholder="phone" value="${requestScope.mv.phone}" name="phone">
				<div class="btnBox">
					<button class="btn" type="button" onclick="check()">수정</button>
					<button class="btn" type="button" onclick="cancel()">탈퇴</button>
				</div>
			</form>
		</section>
	</div>

	<%@ include file="/WEB-INF/footer.jsp" %>