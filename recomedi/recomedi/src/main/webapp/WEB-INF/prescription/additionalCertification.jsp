<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>추가 인증</title>
<style>
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
    width: 80px;
}

.title {
    text-align: center;
    font-size: 24px;
    font-weight: bold;
    margin-top: 20px;
}

.timer {
    color: red;
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

.description {
    margin-top: 20px;
     color: #AAAAAA;
}
</style>
</head>
<body>
<div class="container">
    <div class="logo">
        <img src="logo.png" alt="RECOMEDI 로고">
    </div>
    
    <h1 class="title">본인 인증</h1>

    <div class="input-group">
        <label for="auth-number">인증번호</label>
        <input type="text" id="auth-number" placeholder="숫자 *자리 입력">
        <span class="timer">5:00</span>
    </div>

    <div class="button-group">
        <button type="button" onclick="history.back()">취소</button>
        <button type="submit">확인</button>
    </div>

    <p class="description">
        인증번호 문자를 못 받으셨나요?<br>
        • 입력하신 인증정보가 일치하지 않을 경우, 인증번호 문자는 발송되지 않습니다.<br>
        • 인증번호가 문자로 수신되지 않을 경우 정확한 정보로 재시도 해 주시기 바랍니다.
    </p>
</div>

    <script>
function startTimer(duration, display) {
    let timer = duration, minutes, seconds;

    const interval = setInterval(() => {
        minutes = Math.floor(timer / 60);
        seconds = timer % 60;

        seconds = seconds < 10 ? "0" + seconds : seconds;
        display.textContent = minutes + ":" + seconds;

        if (--timer < 0) {
            clearInterval(interval); // 타이머 종료
            alert("유효시간이 종료되었습니다.");
            window.location.href = '${pageContext.request.contextPath}/prescription/restartProcess.do'; // 재시작 경로
        }
    }, 1000);
}

window.onload = () => {
    const fiveMinutes = 5 * 60; // 타이머 설정 (5분)
    const display = document.querySelector('.timer');
    startTimer(fiveMinutes, display);
};

$('#verify-sms').on('click', function () {
    const smsAuthNumber = $('#auth-number').val();

    $.ajax({
        url: '${pageContext.request.contextPath}/prescription/verifySmsCode.do',
        method: 'POST',
        data: { smsAuthNumber },
        success: function (response) {
            if (response.verified) {
                alert('인증 성공! 최종 결과로 이동합니다.');
                window.location.href = '${pageContext.request.contextPath}/prescription/finalResult.do';
            } else {
                alert('인증번호가 일치하지 않습니다. 다시 시도하세요.');
            }
        },
        error: function (xhr, status, error) {
            console.error('SMS 인증 중 오류 발생:', xhr.responseText);
            alert('SMS 인증 중 오류가 발생했습니다.');
        }
    });
});
</script>
</body>
</html>
