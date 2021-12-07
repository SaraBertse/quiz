<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="controller.*" %>
<!DOCTYPE html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Users</title>
    </head>
    <body>
        
        <h1>Users</h1>
        <table><tr><th>Username</th><th>Password</th></tr>
        <%
            // pre defined variables are request, response, out, session, application
            String pw = (String)session.getAttribute("password");
            String hej = "hej";
           // for(User u : users){
        %>
    <tr>
        <form method="post" action="/Quiz/QuizServlet">
            <input type="hidden" name="action" value="SelectQuiz1">
            <input type="submit" value="SelectQuiz1">
        </form>
           <form method="post" action="/Quiz/QuizServlet">
            <input type="hidden" name="action" value="SelectQuiz2">
            <input type="submit" value="SelectQuiz2">
        </form>
    </tr>

        <%
            
        %>
        </table>
        
    </body>
</html>


 

