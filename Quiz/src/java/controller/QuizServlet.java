/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.DBHandler;
import model.Question;
import model.Result;

/**
 *
 * @author sarab
 */
public class QuizServlet extends HttpServlet {
    int quizType = 0;
    ArrayList<Question> questions;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet QuizServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet QuizServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);
        //int quizType = 0;
        
        ServletContext application = request.getServletContext();
        DBHandler dbh = (DBHandler) application.getAttribute("dbh");
        if (dbh == null) {
            dbh = new DBHandler();
        }
        //List<Question> questions = new List<>();
        
        if ("SelectQuiz1".equals(request.getParameter("action"))) {
            RequestDispatcher rd = request.getRequestDispatcher("/quiz1.jsp");
            quizType = 1;
            questions = dbh.getQuestions(quizType);
            session.setAttribute("questions", questions);
            rd.forward(request, response);
        } 
        else if ("SelectQuiz2".equals(request.getParameter("action"))) {
            RequestDispatcher rd = request.getRequestDispatcher("/quiz2.jsp");
            quizType = 2;
            questions = dbh.getQuestions(quizType);
            session.setAttribute("questions", questions);
            rd.forward(request, response);
        }
        else if ("submitQuiz".equals(request.getParameter("action"))){
            /*int[] q1answers = new int[3];
            int[] q2answers = new int[3];
            int[] q3answers = new int[3];*/
            
            int[][] qAnswers = new int[3][3];
            
            for(int i = 1; i < 4; i++){
                for(int j = 1; j < 4; j++){
                    if(!(request.getParameter("q" + i + "b" + j) == null)){
                        qAnswers[i-1][j-1] = 1;
                    }
                    //else{
                    //    qAnswers[i-1][j-1] = 1;
                    //}
                }
            }
            
            
            int points = 0;
            for(int i = 0; i < 3; i++){
                boolean solvedQuestion = true;
                for(int j = 0; j < 3; j++){
                    if(!(Integer.parseInt(questions.get(i).getCorrect()[j]) == qAnswers[i][j])){
                        solvedQuestion = false;
                    }
                }
                if(solvedQuestion) { points++; }
                
            }
            String usern = (String)application.getAttribute("username");
            dbh.setPoints(dbh.getUserID(usern), quizType, points);
            
            RequestDispatcher rd = request.getRequestDispatcher("/quizResult.jsp");
            ArrayList<Result> pointsHistory; // if default num shows up something is wrong
            pointsHistory = dbh.getResults(quizType, usern);
            session.setAttribute("quiz" + quizType + "History", pointsHistory);
            
            session.setAttribute("points", points);
            rd.forward(request, response);
            
        }
        // activates when pressing "Back" in quizResult
        else if ("toMain".equals(request.getParameter("action"))) {
            RequestDispatcher rd = request.getRequestDispatcher("/mainMenu.jsp");
            rd.forward(request, response);
        }
        
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    //public calcPoints()

}