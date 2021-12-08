<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="controller.*" %>
<%@page import="model.*" %>
<%@page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Main Menu</title>
    </head>
    <body>
        <h1>Choose a quiz!</h1>
        <%
            // pre defined variables are request, response, out, session, application
            
            String pw = (String) session.getAttribute("password");
            String hej = "hej";
            ArrayList<Result> resultList = (ArrayList)session.getAttribute("pointsHistory");
            // for(User u : users){
        %>
        <form method="post" action="/Quiz/QuizServlet">
            <input type="hidden" name="action" value="SelectQuiz1">
            <input type="submit" value="SelectQuiz1">
            <!-- add date: result -->
        </form>
        <form method="post" action="/Quiz/QuizServlet">
            <input type="hidden" name="action" value="SelectQuiz2">
            <input type="submit" value="SelectQuiz2">
        </form>
        <br/>
        <div>
            <h3>Results for Quiz 1:</h3>
            <!-- result list here -->
            <% for(Result r : resultList){
                    out.println(r.getScore());
               }
            %>
        </div>
        <br/>
        <div>
            <h3>Results for Quiz 2:</h3>
        </div>

    </body>
</html>