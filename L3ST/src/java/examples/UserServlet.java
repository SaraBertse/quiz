package examples;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import model.User;
import model.DbHandler;
import model.Result;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping; // for mapping request path to invoking method
import org.springframework.web.bind.annotation.RequestMethod; // for mapping request method (GET, POST) to controller method 
import org.springframework.web.bind.annotation.RequestParam; // for retrieving HTTP parameters sent (GET, POST)
import org.springframework.web.bind.annotation.SessionAttributes; // same as setattribute on a HttpSession-object


//@SessionAttributes("users")
@Controller
public class UserServlet {

    static String userName;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String start() {
        return "index"; // return filename without ".jsp" for jsp-pages in WEB-INF/jsp
    }

    @RequestMapping(value = "/auth", method = RequestMethod.GET) // GET
    public String submit(@RequestParam("action") String action, @RequestParam("username") String username, @RequestParam("password") String gottenPassword, ModelMap model, HttpServletRequest request) {
        String password = "notworking";
        System.out.println("kolla login");
        DbHandler dbh = new DbHandler();
        User[] users = dbh.findUsers();
        model.addAttribute("users", users);

        for (User temp : users) {
            //Checks that the username exists in the database
            if (temp.getUsername().equals(username)) {
                password = gottenPassword;
                password = encodePassw(password);
                if (temp.getPassword().equals(password)) {
                    model.addAttribute("user", temp);
                    userName = temp.getUsername();

                    ArrayList<Result> quiz1History; // if default num shows up something is wrong
                    ArrayList<Result> quiz2History;
                    quiz1History = dbh.getResults(1, userName);
                    quiz2History = dbh.getResults(2, userName);
                    model.addAttribute("quiz1History", quiz1History);
                    model.addAttribute("quiz2History", quiz2History);
                    return "mainMenu.html";
                } else {
                    return "index";
                }
            }
        }
        return "index";
    }

    public String encodePassw(String passw) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] messageDigest = md.digest(passw.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
