<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<title>Board</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet" href="/resources/css/slidebars.css">
<link rel="stylesheet" href="/resources/css/style.css">
<style>
#addBoard, .createBoard, .board {
	width: 150px;
	height: 150px;
	margin: 5px;
	border: 1px solid black;
	float: left;
}

body, #content {
	height: 100%;
}
</style>
<script>
	window.onload = function() {
		$.ajax({
			url : '/main/searchBoard',
			method : 'post'
		}).done(function(msg) {
			var jArr = JSON.parse(msg);
			$.each(jArr, function(i) {
				var b_num = i + 1;
				var div = document.createElement('div');
				var text = '';
				div.id = 'board' + b_num;
				div.className = 'board';

				var aTag = document.createElement('a');
				var createAText = document.createTextNode('프로젝트' + b_num);

				aTag.setAttribute('href', '/main/list?b_num=' + b_num);
				aTag.appendChild(createAText);
				div.appendChild(aTag);

				document.getElementById('viewBoard').appendChild(div);

			});

		});

	};

	function addBoard() {
		$.ajax({
			method : 'post',
			url : '/person/createBoard'

		}).done(function(msg) {
			var obj = JSON.parse(msg);
			var b_num = obj.b_num;

			var div = document.createElement('div');
			div.id = 'board' + b_num;
			div.className = 'board';

			var aTag = document.createElement('a');
			var createAText = document.createTextNode('프로젝트' + b_num);

			aTag.setAttribute('href', 'listView?b_num=' + b_num);
			aTag.appendChild(createAText);
			div.appendChild(aTag);

			document.getElementById('createBoard').appendChild(div);
		});
	}
</script>
</head>
<body>
	<nav>
		<div align="center" class="nav">
			<img src="/resources/images/logo.JPG" alt="Main" class="main_img">
		</div>
	</nav>
	<div class="content">
		<div id="viewBoard"></div>
		<div id="createBoard"></div>
		<div id="addBoard" onclick="addBoard();">Create</div>
	</div>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
	<script src="/resources/js/slidebars.js"></script>
	<script src="/resources/js/scripts.js"></script>
</body>
</html>