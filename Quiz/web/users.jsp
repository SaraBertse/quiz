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
        <%=pw%>
        <%=hej%>
    </tr>

        <%
            
        %>
        </table>

    </body>
</html>