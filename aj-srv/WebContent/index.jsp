<%
//request.get("/pages/login.jsp").
  String mainWeb = request.getContextPath();
response.sendRedirect(mainWeb + "/pages/login.jsp");

%>