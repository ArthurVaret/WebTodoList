<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>WebTodoList</title>
    </head>
    <body>
        <h1>Register</h1>
        <br/>
        <div>
            <form method="POST" action="register">
                <fieldset>
                    <legend> Role </legend>
                    <label for="instructor"> Instructor </label>
                    <input type="radio" id="instructor" name="role" value="instructor">
                    <label for="student"> Student </label>
                    <input type="radio" id="student" name="role" value="student">
                </fieldset>
                <label for="username">Username</label>
                <input type="text" id="username" name="username" placeholder="username">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" placeholder="password">
                <label for="confirm">Confirm password</label>
                <input type="password" id="confirm" name="confirm-password" placeholder="confirm your password">
                <input type="submit" name="submit" value="Register">
            </form>
            <h2>${message}</h2>
        </div>
    </body>
</html>