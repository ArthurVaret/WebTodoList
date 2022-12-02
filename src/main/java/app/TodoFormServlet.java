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

@WebServlet(name = "todoformServlet", value = "/todo-form")
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
            HttpSession session = request.getSession();
            if (session == null) {
                response.setStatus(403);
                response.sendRedirect("/");
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher("/todoform.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String action = request.getParameter("action");
            HttpSession session = request.getSession(false);
            if (action.equals("Logout")) {
                session.invalidate();
                Cookie c = new Cookie("JSESSIONID","");
                c.setMaxAge(0);
                response.addCookie(c);
                response.sendRedirect(request.getContextPath() + "/");
            }

            String description = request.getParameter("description");
            if (DB.todo(description)) {
                request.setAttribute("message","Todo added !");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/todoform.jsp");
                dispatcher.forward(request, response);
            } else {
                request.setAttribute("message","Error");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/todoform.jsp");
                dispatcher.forward(request, response);
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}