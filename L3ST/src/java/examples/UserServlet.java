package examples;

import model.User;
import model.DbHandler;
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

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String start(){ //method name is not mapped
        return "index"; // return filename without ".jsp" for jsp-pages in WEB-INF/jsp
    }
   
    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    public String submit(@RequestParam("username") String username, @RequestParam("password") String password, ModelMap model) {
        System.out.println("kolla login");
        DbHandler dbh = new DbHandler();
        User[] users = dbh.findUsers();
        model.addAttribute("users", users);
        return "users.html"; // for thymeleaf we use the filename and .html, you may return only "users" for the "users.jsp" file.
    }
    
}