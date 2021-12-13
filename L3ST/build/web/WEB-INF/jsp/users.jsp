<%@page import="examples.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>Users</title>
    </head>
    <body>
        <h1>Users (jsp)</h1>
        <table><tr><th>Username</th><th>Password</th></tr>
        <%
            // mostly used pre-defined variables are request, response, out, session, application
            User[] users = (User[])session.getAttribute("users");
            for(User u : users){
        %>
    <tr>
        <td><%= u.getUsername() %></td>
        <td><%= u.getPassword() %></td>
    </tr>

        <%
            }
        %>
        </table>

    </body>
</html>