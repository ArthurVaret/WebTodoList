<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% String message = (String) request.getAttribute("message"); %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Add todo - WebTodoList</title>
    <link type="text/css" rel="stylesheet" href="../style/main.css">
  </head>
  <body>
    <div class="container">
      <h1>Add to do list</h1>
      <form method="POST" action="add">
        <label for="description">Description</label>
        <input class="text-input" type="text" id="description" name="description" placeholder="Todo description">
        <input class="button orange" type="submit" name="action" value="Add">
      </form>
      <c:set var="message" scope="session" value="<%=message%>"/>
      <c:if test="${message != null}">
        <h2><%=message%></h2>
      </c:if>

      <a class="link" href="../todos">> Go back</a>
    </div>
  </body>
</html>
