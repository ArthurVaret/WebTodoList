package app;

import models.Todo;
import models.WtlDBUtil;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;

@WebServlet(name = "editServlet", value = "/todos/edit")
public class EditTodoServlet extends HttpServlet {
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
            System.out.println("[GET] /todos/edit");
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
                response.sendRedirect(request.getContextPath() + "/todos");
                return;
            }
            System.out.println("[!] Instructor verified ! Showing edit form... ");
            int id = Integer.parseInt(request.getParameter("id"));
            Todo todo = DB.getTodo(id);
            if (todo == null) {
                System.out.println("No todo found !");
                response.setStatus(404);
                response.sendRedirect(request.getContextPath() + "/todos");
            } else {
                request.setAttribute("todo",todo);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/edit.jsp");
                dispatcher.forward(request, response);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/");
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            System.out.println("[POST] /todos/edit");
            HttpSession session = request.getSession(false);
            if ((session == null) || !session.getAttribute("role").equals("instructor")) {
                // to-do
                System.out.println("[!] Can't edit, no session or role not set as instructor ");
                response.setStatus(403);
                response.sendRedirect(request.getContextPath() + "/");
                return;
            }
            String description = request.getParameter("description");
            int id = Integer.parseInt(request.getParameter("id"));
            if (DB.updateTodo(id,description)) {
                System.out.println("[+] Todo edited");
            } else {
                request.setAttribute("message", "Error !");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/edit.jsp");
                dispatcher.forward(request, response);
            }
            response.sendRedirect(request.getContextPath() + "/todos");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}