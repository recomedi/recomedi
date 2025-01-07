<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>본인 인증</title>
<style>
/* 기존 스타일 유지 */
body {
    font-family: Arial, sans-serif;
    margin: 0;
    padding: 0;
    background-color: #f5f5f5;
}

.container {
    max-width: 600px;
    margin: 50px auto;
    background-color: #ffffff;
    border-radius: 10px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    padding: 20px;
}

.logo {
    text-align: center;
}

.logo img {
    width: 100px;
}

.title {
    text-align: center;
    font-size: 24px;
    font-weight: bold;
}

.input-group {
    margin-bottom: 20px;
}

.input-group label {
    display: block;
    font-weight: bold;
    margin-bottom: 10px;
}

.input-group input[type="text"] {
    width: calc(100% - 20px);
    padding: 10px;
    border-radius: 5px;
    border: 1px solid #cccccc;
}

.button-group {
    display: flex;
    justify-content: space-between;
}

.button-group button {
    width: 48%;
    padding: 10px;
    border-radius: 5px;
    border: none;
    cursor: pointer;
}

.button-group button:first-child {
    background-color: #cccccc; /* 취소 버튼 */
}

.button-group button:last-child {
    background-color: #003366; /* 확인 버튼 */
    color: white;
}

/* 보안문자 입력 영역 숨김 */
.secure-input {
    display: none; /* 기본적으로 숨김 */
}
</style>
</head>
<body>
<div class="container">
    <div class="logo">
        <img src="src/main/resources/static/images/image71.png" alt="RECOMEDI 로고">
    </div>

    <h1 class="title">본인 인증</h1>

    <!-- 본인 인증 폼 -->
    <form id="certification-form">
        <div class="input-group">
            <label for="id-number-front">주민등록번호 *</label>
            <input type="text" id="id-number-front" name="idNumberFront" maxlength="6" placeholder="주민번호 앞자리" required>
            <input type="password" id="id-number-back" name="idNumberBack" maxlength="7" placeholder="주민번호 뒷자리" required>
        </div>

        <div class="input-group">
            <label for="name">이름</label>
            <input type="text" id="name" name="name" placeholder="이름 입력" required>
        </div>
        
        
        <div class="input-group">
		    <label for="telecom">통신사</label>
		    <select id="telecom" name="telecom" required>
		        <option value="0">SKT</option>
		        <option value="1">KT</option>
		        <option value="2">LG U+</option>
		        <option value="3">알뜰폰(SKT)</option>
		        <option value="4">알뜰폰(KT)</option>
		        <option value="5">알뜰폰(LG U+)</option>
		    </select>
		</div>
        

        <div class="input-group">
            <label for="phonePrefix">휴대폰 번호</label>
            <input type="text" id="phonePrefix" name="phonePrefix" value="010" readonly>
            <input type="text" id="phoneNumber" name="phoneNumber" placeholder="-없이 번호만 입력해주세요" required>
        </div>

     

        <!-- 버튼 -->
        <div class="button-group">
            <button type="button" onclick="history.back()">취소</button>
            <button type="submit">확인</button>
        </div>
    </form>
</div>

<!-- 보안문자 입력 영역 -->
<div class="secure-input">
    <div class="input-group">
        <label for="secureNo">보안 문자</label>
        <img id="secureNoImage" src="" alt="보안 문자 이미지">
        <button type="button" id="refresh-secureNo">새로고침</button> <!-- 새로고침 버튼 추가 -->
        <input type="text" id="secureNo" name="secureNo" placeholder="보안 문자를 입력하세요">
    </div>
    <button type="button" id="secure-submit">보안 문자 확인</button>
</div>
<div class="input-group">
    <label for="sms-auth-number">SMS 인증번호</label>
    <input type="text" id="sms-auth-number" placeholder="인증번호 입력">
</div>

<div class="button-group">
    <button type="button" id="verify-sms">SMS 인증 확인</button>
</div>




<script src="//code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    
    // 본인 인증 폼 제출
$('#certification-form').on('submit', function(e) {
    e.preventDefault(); // 기본 폼 제출 방지

    const formData = $(this).serialize(); // 폼 데이터 직렬화

    $.ajax({
        url: '${pageContext.request.contextPath}/medicine/processCertification.do', // 서버 엔드포인트
        method: 'POST',
        data: formData,
        success: function(response) {
            console.log('응답 데이터:', response);

            if (response.redirectToSecureInput) {
                const base64ImageDecoded = response.reqSecureNoDecoded;

                if (base64ImageDecoded) {
                    $('#secureNoImage').attr('src', base64ImageDecoded); // 이미지 렌더링
                    $('.secure-input').show(); // 보안문자 입력 영역 표시
                    console.log('보안문자 이미지가 렌더링되었습니다.');
                } else {
                    console.error('[ERROR] 보안문자 데이터가 없습니다.');
                    alert('보안문자를 불러오는 데 실패했습니다.');
                }
            } else {
                alert('추가 인증 없이 다음 단계로 진행합니다.');
                window.location.href = '${pageContext.request.contextPath}/medicine/additionalCertification.do';
            }
        },
        error: function(xhr, status, error) {
            console.error('AJAX 요청 실패:', xhr.responseText);
            alert('요청 중 오류가 발생했습니다.');
        }
    });
});

    
    
    
$('#secureNoImage').on('load', function() {
    console.log('보안문자 이미지 로드 성공');
}).on('error', function() {
    console.error('보안문자 이미지 로드 실패');
});


$('#refresh-secureNo').on('click', function () {
    $.ajax({
        url: '${pageContext.request.contextPath}/medicine/refreshSecureNo.do',
        method: 'POST',
        success: function (response) {
            if (response.reqSecureNoDecoded) {
                $('#secureNoImage').attr('src', response.reqSecureNoDecoded); // 서버 응답 그대로 사용
                console.log('보안문자가 새로고침되었습니다.');
            } else {
                alert('새로운 보안문자를 가져오는 데 실패했습니다.');
            }
        },
        error: function (xhr, status, error) {
            console.error('보안문자 새로고침 중 오류 발생:', xhr.responseText);
            alert('보안문자를 새로고침하는 중 오류가 발생했습니다.');
        }
    });
});








    // 보안 문자 확인 버튼 클릭
$('#secure-submit').on('click', function () {
    const secureData = {
        secureNo: $('#secureNo').val(),
        secureNoRefresh: '0',
        is2Way: true,
        jobIndex: sessionStorage.getItem('jobIndex') || 0,
        threadIndex: sessionStorage.getItem('threadIndex') || 0,
        jti: sessionStorage.getItem('jti') || '',
        twoWayTimestamp: sessionStorage.getItem('twoWayTimestamp') || Date.now()
    };

    console.log('[DEBUG] 보안문자 인증 요청 데이터:', secureData);

    $.ajax({
        url: '${pageContext.request.contextPath}/medicine/processSecureInput.do',
        method: 'POST',
        data: secureData,
        success: function (response) {
            console.log('[DEBUG] 추가 인증 응답:', response);

            if (response.success) {
                alert('보안문자 인증 성공! 이제 SMS 인증번호를 입력하세요.');
                $('#sms-auth-number').prop('disabled', false); // SMS 입력 필드 활성화
                $('#verify-sms').prop('disabled', false); // SMS 확인 버튼 활성화
            } else if (response.errorMessage.includes('추가 정보를 입력하세요')) {
                alert('보안문자 인증 성공! 추가 정보 입력이 필요합니다.');
                $('#sms-auth-number').prop('disabled', false); // SMS 입력 필드 활성화
                $('#verify-sms').prop('disabled', false); // SMS 확인 버튼 활성화
            } else {
                alert('보안문자 인증 실패: ' + response.errorMessage);
            }
        },
        error: function (xhr, status, error) {
            console.error('[ERROR] 보안문자 인증 중 오류 발생:', xhr.responseText);
            alert('보안문자 인증 중 오류가 발생했습니다.');
        }
    });
});

    
    
    
    
$('#verify-sms').on('click', function () {
    const smsAuthNumber = $('#sms-auth-number').val();

    if (!smsAuthNumber) {
        alert('SMS 인증번호를 입력하세요.');
        return;
    }

    const smsData = {
    		smsAuthNo: smsAuthNumber,
            is2Way: true,
            jobIndex: sessionStorage.getItem('jobIndex') || 0,
            threadIndex: sessionStorage.getItem('threadIndex') || 0,
            jti: sessionStorage.getItem('jti') || '',
            twoWayTimestamp: sessionStorage.getItem('twoWayTimestamp') || Date.now()
    };

    console.log('[DEBUG] SMS 인증 요청 데이터:', smsData);

    $.ajax({
        url: '${pageContext.request.contextPath}/medicine/verifySmsCode.do',
        method: 'POST',
        data: smsData,
        success: function (response) {
            console.log('[DEBUG] SMS 인증 응답:', response);

            if (response.verified) {
                alert('SMS 인증 성공! 최종 결과 페이지로 이동합니다.');
                window.location.href = '${pageContext.request.contextPath}/medicine/finalResult.do';
            } else {
                alert(response.message || 'SMS 인증 실패');
            }
        },
        error: function (xhr, status, error) {
            console.error('[ERROR] SMS 인증 중 오류 발생:', xhr.responseText);
            alert('SMS 인증 중 오류가 발생했습니다.');
        }
    });
});







</script>
</body>
</html>
