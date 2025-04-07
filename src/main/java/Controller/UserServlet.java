package Controller;

import Dao.UserDao;
import Model.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/")
public class UserServlet extends HttpServlet {

    UserDao userDao = new UserDao();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        switch (action) {
            case "/new":
                try{
                    showNewForm(request, response); break;
                } catch (ServletException e) {
                    e.printStackTrace();
                }
            case "/insert":
                try{
                    insertUser(request, response); break;
                } catch (ServletException e) {
                    e.printStackTrace();
                }
            case "/delete":
                try{
                    deleteUser(request, response); break;
                } catch (ServletException e) {
                    e.printStackTrace();
                }
            case "/edit":
                try{
                    showEditForm(request, response); break;
                } catch (ServletException e) {
                    e.printStackTrace();
                }
            case "/update":
                try{
                    updateUser(request, response); break;
                } catch (ServletException e) {
                    e.printStackTrace();
                }
            default:
                try {
                    listUser(request, response);
                    break;
                } catch (ServletException e) {
                    e.printStackTrace();
                }
        }


    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/View/user-form.jsp");
        dispatcher.forward(request, response);
    }

    private void insertUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("name");
        String email = request.getParameter("email");
        String country = request.getParameter("country");
        User newUser = new User(username, email, country);

        UserDao.insertUser(newUser);
        response.sendRedirect("list");
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        try{
            userDao.deleteUserById(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        response.sendRedirect("list");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        User existingUser;
        try{
            existingUser = userDao.selectUserById(id);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/View/user-form.jsp");
            request.setAttribute("user", existingUser);
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String username = request.getParameter("name");
        String email = request.getParameter("email");
        String country = request.getParameter("country");
        System.out.println(id+" "+username + " " + email + " " + country);
        User user = new User(id, username, email, country);
        userDao.updateUser(user);
        response.sendRedirect("list");
    }

    private void listUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            List<User> listUser = userDao.selectAllUsers();
            request.setAttribute("listUser", listUser);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/View/user-list.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
