/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author sarab
 */
public class DBHandler {
       private static Connection connection;
       private PreparedStatement findUser;
       private PreparedStatement getUserCount;
       int userCount;
       User[] users;
       

         
       public static void connToDB(){
          try{
           connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quiz","admin", "admin");
           System.out.println("connection successful");
          }
          catch(Exception e){
                System.out.println("cannot connect");
               e.printStackTrace();
          }
       }
       
    public User[] findUsers() {
        try {
            connToDB();
            getUserCount = connection.prepareStatement("SELECT COUNT(*) FROM users");
            ResultSet countResult = getUserCount.executeQuery();
            countResult.next();
            userCount = countResult.getInt("COUNT(*)");
            findUser = connection.prepareStatement("SELECT * FROM users");
            //        + " WHERE username = ?");
            //findUser.setString(1, username);
            int i = 0;
           // users = new User[userCount];
            users = new User[2];
            ResultSet result = findUser.executeQuery();
            while (result.next()) {
                User u = new User();
                u.setUsername(result.getString("username"));
                u.setPassword(result.getString("password"));
                users[i] = u;
                i++;
            }

            //  password="notnull";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
    
       public User[] getUsers() {
          User[] users = new User[2];
          users[0] = new User();
          users[0].setUsername("ada@kthse");
          users[0].setPassword("12345");
          users[1] = new User();
          users[1].setUsername("beda@kth.se");
          users[1].setPassword("qwerty");
          return users;
    
    }
       

}
