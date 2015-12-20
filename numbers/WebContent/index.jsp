<%@page import="java.io.*,java.util.*" %>

<html>
<head>
<title>Spring MVC Tutorial by Crunchify - Hello World Spring MVC
	Example</title>
</head>
<body>
<%

String url = request.getRequestURL().toString();
String baseUrl = url.substring(0, url.length() - request.getRequestURL().length()) + request.getContextPath() + "/";

String redirectUrl = baseUrl + "home.jsp";
response.setStatus(response.SC_MOVED_TEMPORARILY);
response.setHeader("Location", redirectUrl);

%>
</body>
</html>