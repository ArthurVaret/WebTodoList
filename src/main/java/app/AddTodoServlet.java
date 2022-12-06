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

@WebServlet(name = "addServlet", value = "/todos/add")
public class AddTodoServlet extends HttpServlet {
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
            System.out.println("[GET] /todos/add");
            HttpSession session = request.getSession(false);
            if (session == null) {
                System.out.println("[!] No session");
                response.setStatus(403);
                response.sendRedirect(request.getContextPath() + "/");
                return;
            }
            System.out.println("[!] User logged, checking role...");
            System.out.println("User : " + session.getAttribute("username") + ", " + session.getAttribute("role"));
            if (!session.getAttribute("role").equals("instructor")) {
                System.out.println("[!] Not instructor, redirecting... ");
                response.setStatus(403);
                response.sendRedirect(request.getContextPath() + "/todo");
                return;
            }
            System.out.println("[!] Instructor verified ! Showing add form... ");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/add.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            System.out.println("[POST] /todos/add");
            HttpSession session = request.getSession(false);
            if ((session == null) || !session.getAttribute("role").equals("instructor")) {
                // to-do
                System.out.println("[!] Can't add, no session or role not set as instructor ");
                response.setStatus(403);
                response.sendRedirect(request.getContextPath() + "/");
                return;
            }
            String description = request.getParameter("description");
            if (DB.registerTodo(new Todo(description))) {
                System.out.println("[+] Todo added");
            } else {
                request.setAttribute("message", "Error !");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/add.jsp");
                dispatcher.forward(request, response);
            }
            response.sendRedirect(request.getContextPath() + "/todos");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}