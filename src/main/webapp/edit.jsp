<%@ page import="models.Todo" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% String message = (String) request.getAttribute("message"); %>
<% Todo todo = (Todo) request.getAttribute("todo"); %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Edit todo - WebTodoList</title>
    <link type="text/css" rel="stylesheet" href="../style/main.css">
  </head>
  <body>
    <div class="container">
      <h1>Edit to do list</h1>
      <p>Id: <%=todo.getId()%></p>
      <form method="POST" action="edit">
        <label for="description">Description</label>
        <input type="hidden" name="id" value="<%=todo.getId()%>">
        <input class="text-input" type="text" id="description" name="description" placeholder="Todo description" value="<%=todo.getDescription()%>">
        <input class="button orange" type="submit" name="action" value="Edit">
      </form>
      <c:set var="message" scope="session" value="<%=message%>"/>
      <c:if test="${message != null}">
        <h2><%=message%></h2>
      </c:if>

      <a class="link" href="../todos">> Go back</a>
    </div>
  </body>
</html>
