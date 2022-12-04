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

@WebServlet(name = "todoServlet", value = "/todos")
public class TodosServlet extends HttpServlet {
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
            if (session == null || session.getAttribute("id") == null ) {
                System.out.println("[!] No session");
                response.setStatus(403);
                response.sendRedirect(request.getContextPath() + "/");
            } else {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/todos.jsp");
                dispatcher.forward(request, response);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String action = request.getParameter("action");
            System.out.println("Action : " + action);
            HttpSession session = request.getSession(false);
            if (action.equals("Logout")) {

                Cookie[] cookies = request.getCookies();
                if (cookies != null) {
                    for (Cookie c: cookies) {
                        if (c.getName().equals("JSESSIONID")) {
                            c.setMaxAge(0);
                            response.addCookie(c);
                            break;
                        }
                    }
                }
                if (session != null)
                    session.invalidate();
                response.sendRedirect(request.getContextPath()+"/");
            } else if (action.equals("Add")) {
                System.out.println("[#] Instructor want to add a to do, redirecting to form...");
                response.sendRedirect(request.getContextPath()+"/todo-form");
            } else {
                System.out.println("[#] No action specified, redirecting nowhere");
                response.sendRedirect(request.getContextPath());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}