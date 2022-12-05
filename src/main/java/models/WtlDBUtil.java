package models;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WtlDBUtil {
    private final DataSource dataSource;
    public WtlDBUtil(DataSource theDataSource) {
        dataSource = theDataSource;
    }

    public List<User> getUsers() throws Exception {
        List<User> students = new ArrayList<>();
        Connection c =null;
        Statement s = null;
        ResultSet r = null;
        try {
            c = dataSource.getConnection();
            s= c.createStatement();
            String sql = "SELECT * FROM users";
            r = s.executeQuery(sql);
            while(r.next()){
                int id = r.getInt("id");
                String username = r.getString("username");
                String password = r.getString("password");
                String role = r.getString("role");
                User user= new User(id,username,password,role);
                students.add(user);
            }
            return students;
        } finally{
            close(c,s,r);
        }
    }

    public boolean userExist(String username) {
        Connection c = null;
        PreparedStatement s = null;
        ResultSet r = null;
        try {
            c = dataSource.getConnection();
            String sql = "SELECT * FROM users WHERE username = ? ";
            s = c.prepareStatement(sql);
            s.setString(1, username);

            r = s.executeQuery();
            return r.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            close(c,s,r);
        }
    }

    public User login(String username, String hashedPassword) {
        User u = null;
        Connection c = null;
        PreparedStatement s = null;
        ResultSet r = null;
        try {
            c = dataSource.getConnection();
            String sql = "SELECT * FROM users WHERE username = ? AND password = ? ";
            s = c.prepareStatement(sql);
            s.setString(1, username);
            s.setString(2, hashedPassword);
            r = s.executeQuery();
            if (r.next()) {
                u = new User(
                        r.getInt("id"),
                        r.getString("username"),
                        r.getString("password"),
                        r.getString("role")
                );
            }
            return u;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            close(c,s,r);
        }
    }

    public boolean register(User u) {
        Connection c = null;
        PreparedStatement s = null;
        try {
            c = dataSource.getConnection();
            String sql = "INSERT INTO users(username,password,role) VALUES (?,?,?)";
            s = c.prepareStatement(sql);
            s.setString(1, u.getUsername());
            s.setString(2, u.getPassword());
            s.setString(3, u.getRole());
            int r = s.executeUpdate();
            return r == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            close(c,s,null);
        }
    }

    public List<Todo> getTodos() throws Exception{
        List<Todo> todos = new ArrayList<>();
        Connection c =null;
        Statement s = null;
        ResultSet r = null;
        try {
            c = dataSource.getConnection();
            s= c.createStatement();
            String sql = "SELECT * FROM todos";
            r = s.executeQuery(sql);
            while(r.next()){
                int id = r.getInt("id");
                String description = r.getString("description");
                Todo todo = new Todo(id,description);
                todos.add(todo);
            }
            return todos;
        } finally{
            close(c,s,r);
        }
    }

    public Todo getTodo(int id) {
        Connection c = null;
        PreparedStatement s = null;
        ResultSet r = null;
        try {
            c = dataSource.getConnection();
            String sql = "SELECT * FROM todos WHERE id = ?";
            s = c.prepareStatement(sql);
            s.setInt(1,id);

            r = s.executeQuery();
            Todo t = null;
            if (r.next()) {
                t = new Todo(
                    r.getInt("id"),
                    r.getString("description")
                );
            }
            return t;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            close(c,s,r);
        }
    }

    public boolean registerTodo(Todo t) {
        Connection c = null;
        PreparedStatement s = null;
        try {
            c = dataSource.getConnection();
            String sql = "INSERT INTO todos(description) VALUES (?)";
            s = c.prepareStatement(sql);
            s.setString(1,t.getDescription());

            int r = s.executeUpdate();
            return r == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            close(c,s,null);
        }
    }

    public boolean updateTodo(int id, String description) {
        Connection c = null;
        PreparedStatement s = null;
        try {
            c = dataSource.getConnection();
            String sql = "UPDATE todos SET description = ? WHERE id = ?";
            s = c.prepareStatement(sql);
            s.setString(1,description);
            s.setInt(2, id);
            int r = s.executeUpdate();
            return r == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            close(c,s,null);
        }
    }

    public boolean deleteTodo(int id) {
        Connection c = null;
        PreparedStatement s = null;
        try {
            c = dataSource.getConnection();
            String sql = "DELETE FROM todos WHERE id = ?";
            s = c.prepareStatement(sql);
            s.setInt(1,id);

            int r = s.executeUpdate();
            return r == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            close(c,s,null);
        }
    }

    private void close(Connection myConn, Statement myStmt, ResultSet myRs) {
        try{
            if(myStmt!=null)
                myStmt.close();
            if(myRs!=null)
                myRs.close();
            if(myConn!=null)
                myConn.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }



}
