package examples;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
//import javax.servlet.RequestDispatcher;
import model.User;
import model.DbHandler;
import model.Result;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping; // for mapping request path to invoking method
import org.springframework.web.bind.annotation.RequestMethod; // for mapping request method (GET, POST) to controller method 
import org.springframework.web.bind.annotation.RequestParam; // for retrieving HTTP parameters sent (GET, POST)
import org.springframework.web.bind.annotation.SessionAttributes; // same as setattribute on a HttpSession-object
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.bind.annotation.ModelAttribute;

@SessionAttributes("users")
@Controller
public class UserServlet {
    static String userName;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String start(){ //method name is not mapped
        return "index"; // return filename without ".jsp" for jsp-pages in WEB-INF/jsp
    }
   
    @RequestMapping(value = "/auth", method = RequestMethod.GET) // GET
    public String submit(@RequestParam("action") String action, @RequestParam("username") String username, @RequestParam("password") String gottenPassword, ModelMap model, HttpServletRequest request) {
        String password = "notworking";
        System.out.println("kolla login");
        DbHandler dbh = new DbHandler();
        User[] users = dbh.findUsers();
        model.addAttribute("users", users);
        //return "users.html"; // for thymeleaf we use the filename and .html, you may return only "users" for the "users.jsp" file.
        
        for(User temp : users) {
                //Checks that the username exists in the database
                if (temp.getUsername().equals(username)){
                    //password = request.getParameter("password");
                    //RequestDispatcher rd;
                    password = gottenPassword;
                    if (temp.getPassword().equals(password)){
                        model.addAttribute("user", temp);
                        userName = temp.getUsername();
                        
                        ArrayList<Result> quiz1History; // if default num shows up something is wrong
                        ArrayList<Result> quiz2History;
                        quiz1History = dbh.getResults(1, userName);
                        quiz2History = dbh.getResults(2, userName);
                        model.addAttribute("quiz1History", quiz1History);
                        model.addAttribute("quiz2History", quiz2History);
                        return "mainMenu.html";
                            
//rd = request.getRequestDispatcher("/mainMenu.jsp");
                            //application.setAttribute("username", request.getParameter("username"));
                            //rd.forward(request, response);
                    } else {
                        return "index";//.html";
                        //rd = request.getRequestDispatcher("/indexcopy.html");
                        //rd.forward(request, response);
                    }
                } 
            }
            //RequestDispatcher rd = request.getRequestDispatcher("/indexcopy.html"); //Ska gå in på quizzen ist
            //rd.forward(request, response);
            return "index";//.html";
            
      
    }
    
    @ModelAttribute("username")
    public String getUser(HttpServletRequest request) {
        return (String) request.getAttribute("username");
    }
    
}