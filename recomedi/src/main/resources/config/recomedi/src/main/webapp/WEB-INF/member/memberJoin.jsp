<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	<%@ include file="/WEB-INF/header.jsp" %>
  <script src="https://code.jquery.com/jquery-latest.min.js"></script>  <!-- CDN주소 -->

  <script>
  
  const email = /[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[0-9a-zA-Z]$/i;
  const phone = /^\d{1,15}$/;  // 9 ~ 11자의 숫자만 사용
  
  function check() {
	  
	  // 유효성 검사하기
	  let fm = document.frm;

	  if (fm.id.value == "") {
		  alert("이메일을 입력해주세요");
		  fm.id.focus();
		  return;
	  } else if (email.test(fm.id.value) == false) {
		  alert("이메일 형식이 올바르지 않습니다");
		  fm.id.value = "";
		  fm.id.focus();
		  return;
	  } else if ($("#btn").val() == "N") {
		  alert("이메일 중복체크를 해주세요");
		  fm.id.focus();
		  return;
	  } else if (fm.pwd.value == "") {
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
	  } else if (fm.name.value == "") {
		  alert("이름을 입력해주세요");
		  fm.name.focus();
		  return;
	  } else if (fm.nickname.value == "") {
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
	  	  	  
	  let ans = confirm("저장하시겠습니까?");	  
	  if (ans == true) {
		  fm.action="${pageContext.request.contextPath}/member/memberJoinAction.do";
		  fm.method="post";
		  fm.submit();
	  }
	  
	  return;
  }
  
	$(document).ready(function() {
		
	  $("#btn").click(function() {

		  let fm = document.frm;
		  let id = $("#id").val();
		  if (id == "") {
			  alert("이메일을 입력해주세요");
			  return;
		  } else if (email.test(fm.id.value) == false) {
			  alert("이메일 형식이 올바르지 않습니다");
			  fm.id.value = "";
			  fm.id.focus();
			  return;
		  }
		  
		  $.ajax({
			  type: "post",
			  url: "${pageContext.request.contextPath}/member/memberIdCheck.do",
			  dataType: "json",
			  data: {"id": id},
			  success: function(result) {

				  if (result.cnt == 0) {
					  alert("사용할 수 있는 이메일입니다.");
					  $("#btn").val("Y");
				  } else {
					  alert("사용할 수 없는 이메일입니다.");
					  $("#id").val("");
				  }
			  },
			  error: function(xhr, status, error) {
				alert("전송실패 테스트");
 			    /* console.log("Error Status: " + status);
			    console.log("Error Detail: " + error);
			    console.log("Response: " + xhr.responseText); */
			  }
		  });
	  })
  })
  </script>
 
 	<div class="wrapper flex">
		<section class="member shadow join">
			<img src="${pageContext.request.contextPath}/resources/images/bg_join.png" alt="회원가입" class="bg block">
			<h2 class="regular title center">회원가입</h2>
			<form name="frm">
				<label for="id" class="block">이메일</label>
				<div class="flex">
					<input type="text" id="id" placeholder="Email" name="id">
					<button class="btn" type="button" id="btn" value="N">중복체크</button>
				</div>
				<label for="password" class="block">비밀번호</label>
				<input type="password" id="password" placeholder="Password" name="pwd">
				<label for="RecheckPassword" class="block">비밀번호확인</label>
				<input type="password" id="RecheckPassword" placeholder="Recheck password" name="RecheckPassword">
				<label for="name" class="block">이름</label>
				<input type="text" id="name" placeholder="Name" name="name">
				<label for="nickname" class="block">닉네임</label>
				<input type="text" id="nickname" placeholder="Nick name" name="nickname">
				<label for="phone" class="block">연락처</label>
				<input type="text" id="phone" placeholder="Phone" name="phone">
				<div class="btnBox">
					<button class="btn" type="button" onclick="check()">회원가입</button>
				</div>
			</form>
		</section>
	</div>
        
    <%@ include file="/WEB-INF/footer.jsp" %>