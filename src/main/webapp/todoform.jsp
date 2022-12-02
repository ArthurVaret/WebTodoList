<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Add todo list</title>
  <link type="text/css" rel="stylesheet" href="style/main.css">
</head>
<body>
<div class="container">
  <h1>New to do</h1>
  <form method="POST" action="todo-form">
    <label for="description">Description</label>
    <input class="text-input" type="text" id="description" name="description" placeholder="Todo description">
    <input class="link" type="submit" value="Add">
  </form>
  <h2>${message}</h2>
  <a href="todos">Go back</a>
</div>
</body>
</html>
