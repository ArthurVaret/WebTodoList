package app;

import models.Todo;
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
import java.util.List;

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
            if (session == null) {
                System.out.println("[!] No session, redirecting to home");
                String url = request.getContextPath() + "/";
                System.out.println(url);
                response.setStatus(403);
                response.sendRedirect(url);
                return;
            }
            System.out.println("[!] User logged, showing todos...");

            List<Todo> todos = DB.getTodos();
            request.setAttribute("todos", todos);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/todos.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("role") == null) {
                System.out.println("[!] No session, redirecting to home");
                String url = request.getContextPath() + "/";
                System.out.println(url);
                response.setStatus(403);
                response.sendRedirect(url);
                return;
            }

            String action = request.getParameter("action");
            System.out.println("Action : " + action);
            boolean isInstructor = session.getAttribute("role").equals("instructor");
            if (action.equals("Logout")) {
                Cookie[] cookies = request.getCookies();
                // Search for session cookie and removing it
                if (cookies != null) {
                    for (Cookie c: cookies) {
                        if (c.getName().equals("JSESSIONID")) {
                            c.setMaxAge(0);
                            response.addCookie(c);
                            break;
                        }
                    }
                }
                // Invalidate session
                session.invalidate();
                response.sendRedirect(request.getContextPath()+"/");
            } else if (action.equals("Add") && isInstructor) {
                System.out.println("[#] Instructor want to add a to do, redirecting to form...");
                String url = request.getContextPath() + "/todos/add" ;
                System.out.println("> " + url);
                response.sendRedirect(url);
            } else if (action.equals("Edit") && isInstructor) {
                System.out.println("[#] Instructor want to edit a to do, redirecting to form...");
                int id = Integer.parseInt(request.getParameter("id"));
                String url = request.getContextPath() + "/todos/edit?id=" + id ;
                System.out.println("> " + url);
                response.sendRedirect(url);
            } else if(action.equals("Delete") && isInstructor) {
                System.out.println("[#] Instructor want to delete a to do...");
                int id = Integer.parseInt(request.getParameter("id"));
                if (DB.deleteTodo(id)) request.setAttribute("message","Todo deleted");
                else request.setAttribute("message","Error !");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/todos.jsp");
                dispatcher.forward(request, response);
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