package app;

import models.User;
import models.WtlDBUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;

@WebServlet(name = "loginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    private WtlDBUtil DB;
    private DataSource dataSource;

    private DataSource getDataSource() throws NamingException {
        String jndi="java:comp/env/jdbc/webtodolist" ;
        Context context = new InitialContext();
        return (DataSource) context.lookup(jndi);
    }

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            dataSource = getDataSource();
            DB = new WtlDBUtil(dataSource);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("username") == null || session.getAttribute("role") == null) {
                System.out.println("[#] No session, checking cookies");
                Cookie c = null;
                if (request.getCookies() != null) {
                    for (Cookie cookie:request.getCookies()) {
                        if (cookie.getName().equals("username")) {
                            c = cookie;
                            break;
                        }
                    }
                }
                if (c != null)  request.setAttribute("username",c.getValue());
                else            request.setAttribute("username", "");
                System.out.println("[#] Sending login html");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
                dispatcher.forward(request, response);
                return;
            }
            System.out.println("[#] Session exists, redirecting to todo list");
            System.out.println("User : " + session.getAttribute("username") + ", " + session.getAttribute("role"));
            response.sendRedirect(request.getContextPath() + "/todos");

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            if (username == null || username.equals("") ||
                password == null || password.equals("")) {
                request.setAttribute("message", "Please fill all fields");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
                dispatcher.forward(request, response);
                return;
            }

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest((username+":"+password).getBytes(StandardCharsets.UTF_8));
            String hashedPassword = bytesToHex(hash);
            User u = DB.login(username,hashedPassword);
            if (u != null) {
                // user found

                // Sessions
                HttpSession session = request.getSession(true);
                session.setAttribute("id",u.getId());
                session.setAttribute("username", u.getUsername());
                session.setAttribute("role", u.getRole());

                // Cookies
                Cookie c = new Cookie("username", username);
                response.addCookie(c);
                response.sendRedirect(request.getContextPath() + "/todos");
            } else {
                // no match for user and password
                request.setAttribute("message", "User or password incorrect");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
                dispatcher.forward(request, response);
                throw new IllegalArgumentException("User or password incorrect");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

}