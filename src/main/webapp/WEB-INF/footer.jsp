<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
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

	<script>
		// menu
		const btnMenu = document.querySelector("#hd .btnMenu");
		const sideBar = document.querySelector(".sideBar");
		const btnMenuClose = document.querySelector(".sideBar .close");

		function sideBarOpen() {
			sideBar.classList.add("open");
			btnMenu.classList.add("none");
		}
		btnMenu.addEventListener("click", sideBarOpen);
		
		function sideBarClose() {
			sideBar.classList.remove("open");
			btnMenu.classList.remove("none");
		}
		btnMenuClose.addEventListener("click", sideBarClose);

	</script>
</body>
</html>