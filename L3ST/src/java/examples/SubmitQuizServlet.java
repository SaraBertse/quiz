/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examples;

import static examples.UserServlet.userName;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import model.DbHandler;
import model.Question;
import model.Result;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 *
 * @author Sara Bertse and Jacob Dwyer
 */
@Controller
public class SubmitQuizServlet {

    ArrayList<Question> questions;

    @RequestMapping(value = "/SubmitQuizServlet", method = RequestMethod.POST)
    public String submit(@RequestParam("action") String action,
            ModelMap model, @RequestParam(name = "q", required = false) String[] checkboxValue,
            HttpServletRequest request) {

        DbHandler dbh = new DbHandler();
        int quizType = 0;

        if ("submitQuiz1".equals(action)) {
            questions = dbh.getQuestions(1);
            quizType = 1;
        }

        if ("submitQuiz2".equals(action)) {
            questions = dbh.getQuestions(2);
            quizType = 2;
        }

        int[][] qAnswers = new int[3][3];

        String value = "ch";

        String[] chvalues = {"empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty"};
        String check = "ch";

        //Fills the chvalues-array, which will either hold "empty" or "ch+index"
        //depending on what/how many values are in the checkboxValue array
        int index = 0;
        //checks first if checkboxValue is null, which it can be if no boxes are ticked
        if (checkboxValue != null) {
            for (int i = 1; i < 10; i++) {
                check = "ch";
                check = check + (i);
                if (check.equals(checkboxValue[index])) {
                    chvalues[i - 1] = check;
                    if (index < checkboxValue.length - 1) {
                        index++;
                    }
                }
            }
        }

        index = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (!(chvalues[index].equals("empty"))) {
                    qAnswers[i][j] = 1;

                } else {
                    qAnswers[i][j] = 0;
                }
                index++;
            }
        }

        int points = 0;
        for (int i = 0; i < 3; i++) {
            boolean solvedQuestion = true;
            for (int j = 0; j < 3; j++) {
                if (!(Integer.parseInt(questions.get(i).getCorrect()[j]) == qAnswers[i][j])) {
                    solvedQuestion = false;
                }
            }
            if (solvedQuestion) {
                points++;
            }
        }

        dbh.setPoints(dbh.getUserID(userName), quizType, points);

        ArrayList<Result> quiz1History; // if default num shows up something is wrong
        ArrayList<Result> quiz2History;
        quiz1History = dbh.getResults(1, userName);
        quiz2History = dbh.getResults(2, userName);
        model.addAttribute("quiz1History", quiz1History);
        model.addAttribute("quiz2History", quiz2History);

        model.addAttribute("points", points);
        return "quizResult.html";
    }
}
