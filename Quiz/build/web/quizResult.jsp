<%-- 
    Document   : quizResult
    Created on : 8 dec. 2021, 15:35:49
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quiz Result</title>
    </head>
    <% int points = (Integer)session.getAttribute("points"); %>
    <body>
        <h1>You got <%=points%> right out of 3!</h1>
        <form method="post" action="/Quiz/QuizServlet">
            <input type="hidden" name="action" value="toMain">
            <input type="submit" value="ToMain">
        </form>
    </body>
</html>
