<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
	<head>
		<title>First Project - Hello</title>
			
		<script src="/scripts/jquery-3.7.1.js"></script>
		
		<!--<script src="https://code.jquery.com/jquery-3.7.1.js" 
		integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" 
		crossorigin="anonymous"></script>-->
		
		<script type="text/javascript">
			$( function(){
					//해당 코드의 ID가 frm인 내용을 검색
					let frm = $("#frm");
		
					//해당 코드의 class가 frm인 내용을 검색
					//let frm = $(".frm");	
					
					$("#sibmitBtn").click(function(){
						console.log("전송버튼");
						let name = $("#name");
						//let dept = $("#dept");
						
						if(name.val() == "" ){
							alert("이름 입력");
							name.focus();
							return;
						}
						if($("#pwd").val() == ""){
							alert("비밀번호 입력");
							$("#pwd").focus();
							return;
						}
						
						frm.attr("method", "post");
						frm.attr("action", "/confrimSample");
						frm.submit();
						
					});
					
					$("#canCleBtn").click(function(){
						console.log("취소버튼");
						alert("취소버튼 클릭");
						
					});
					
			})
		</script>
				
	</head>
	<body>
		<form id="frm" name="frm">
			<input type = "text" id ="name" name="name">
			<input type = "text" id = "pwd" name="pwd">
		</form>
			<input type ="button" name="submitBtn" id="sibmitBtn" value="전송"/>&nbsp;&nbsp;
			<button type="button" name="canCleBtn" id="canCleBtn">취소 </button><br><br>

	</body>
	<footer>
		copyright
	</footer>
</html>

