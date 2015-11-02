<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript"> 
      $(document).ready( function() {
    	  setTimeout(function(){ $("#err").fadeIn(); }, 10);
          setTimeout(function(){ $("#err").fadeOut(); }, 4000);
      });
   </script>
</head>
<body>
<br/>
<center>
	<div id="err" style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
		<div id="errZone" style="float:center; margin-left: 7px">Feedback Already Captured</div>        
	</div>
</center>
</body>
</html>