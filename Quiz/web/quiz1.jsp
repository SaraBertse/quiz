<%-- 
    Document   : quiz1
    Created on : 7 dec. 2021, 16:15:10
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="model.Question" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quiz 1</title>
    </head>
    <body>
        <h1>This is Quiz 1</h1>
        
        <%
            // pre defined variables are request, response, out, session, application
            List<Question> ql = (List<Question>)session.getAttribute("questions");
            Question q1st = ql.get(0);
            Question q2nd = ql.get(1);
            Question q3rd = ql.get(2);
           // for(User u : users){
        %>
        <form action="/Quiz/QuizServlet" method="POST">
        <div>
            <h3><%=q1st.getText()%></h3>
            <input type="checkbox" id="q1b1" value="" name="q1b1">
            <label for="q1b1"><%=q1st.getOptions()[0]%></label></br>
            <input type="checkbox" id="q1b2" value="" name="q1b2">
            <label for="q1b2"><%=q1st.getOptions()[1]%></label></br>
            <input type="checkbox" id="q1b3" value="" name="q1b3">
            <label for="q1b3"><%=q1st.getOptions()[2]%></label></br>
        </div>
        <p/>
        <div>
            <h3><%=q2nd.getText()%></h3>
            <input type="checkbox" id="q2b1" value="" name="q2b1">
            <label for="q2b1"><%=q2nd.getOptions()[0]%></label></br>
            <input type="checkbox" id="q2b2" value="" name="q2b2">
            <label for="q2b2"><%=q2nd.getOptions()[1]%></label></br>
            <input type="checkbox" id="q2b3" value="" name="q2b3">
            <label for="q2b3"><%=q2nd.getOptions()[2]%></label></br>
        </div>
        <p/>
        <div>
            <h3><%=q3rd.getText()%></h3>
            <input type="checkbox" id="q3b1" value="" name="q3b1">
            <label for="q3b1"><%=q3rd.getOptions()[0]%></label></br>
            <input type="checkbox" id="q3b2" value="" name="q3b2">
            <label for="q3b2"><%=q3rd.getOptions()[1]%></label></br>
            <input type="checkbox" id="q3b3" value="" name="q3b3">
            <label for="q3b3"><%=q3rd.getOptions()[2]%></label></br>
        </div>

        <input type="hidden" name="action" value="submitQuiz">
        <input type="submit" value="Submit">
        </form>
    </body>
</html>
