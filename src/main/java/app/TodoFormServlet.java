package app;

import models.WtlDBUtil;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;
import java.io.IOException;

@WebServlet(name = "todoformServlet", value = "/todos/form")
public class TodoFormServlet extends HttpServlet {
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
            if (session != null) {
                System.out.println("[!] User logged, checking role...");
                System.out.println("User : " + session.getAttribute("username") + ", " + session.getAttribute("role"));
                if (!session.getAttribute("role").equals("instructor")) {
                    System.out.println("[!] Not instructor, redirecting... ");
                    response.setStatus(403);
                    response.sendRedirect(request.getContextPath() + "/todo");
                } else {
                    System.out.println("[!] Instructor verified ! Showing form... ");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/form.jsp");
                    dispatcher.forward(request, response);
                }
            } else {
                System.out.println("[!] No session or role not set as instructor ");
                response.setStatus(403);
                response.sendRedirect(request.getContextPath() + "/");

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute("role").equals("instructor")) {
                // to-do
                String action = request.getParameter("action");
            } else {
                System.out.println("[!] Can't add, no session or role not set as instructor ");
                response.setStatus(403);
                response.sendRedirect(request.getContextPath() + "/");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}