package Dao;

import Model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private static final String URL = "jdbc:mysql://localhost:3306/UserDb";
    private static final String USER = "root";
    private static final String PASSWORD = "password";
    private static final String DRIVER = "com.mysql.jdbc.Driver";

    private static final String INSERT_USERS_SQL = "insert into users" + "(name,email, country) values(?,?,?)";
    private static final String SELECT_USER_BY_ID = "select * from users where id=?";
    private static final String SELECT_ALL_USERS = "select * from users";
    private static final String DELETE_USER_BY_ID = "delete from users where id=?";
    private static final String UPDATE_USER_BY_ID = "update users set name=?,email=?,country=? where id=?";

    public UserDao() {}

    protected static Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName(DRIVER);//<%--<%@ page import="static jdk.javadoc.internal.doclets.formats.html.markup.HtmlStyle.title" %>--%>

            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void insertUser(User user) {
        System.out.println(INSERT_USERS_SQL);

        try(Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL);) {
                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, user.getEmail());
                preparedStatement.setString(3, user.getCountry());
                System.out.println(preparedStatement);
                preparedStatement.executeUpdate();
        }catch (SQLException e){
            printSQLException(e);
        }
    }

    public User selectUserById(int id) {
        User user = null;
        try(Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);){
            preparedStatement.setInt(1,id);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                user = new User(id,name, email, country);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return user;
    }

    public List<User> selectAllUsers() {
        List<User> users = new ArrayList<>();
        try(Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);) {
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                users.add(new User(id, name, email, country));
            }
        }catch (SQLException e){
            printSQLException(e);
        }
        return users;
    }

    public boolean updateUser(User user) {
        boolean rowUpdated = false;
        try(Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(UPDATE_USER_BY_ID);) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getCountry());
            statement.setInt(4, user.getId());

            rowUpdated = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            printSQLException(e);
        }
        return rowUpdated;
    }

    public boolean deleteUserById(int id) {
        boolean rowDeleted = false;
        try(Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE_USER_BY_ID);){
            statement.setInt(1,id);
            rowDeleted = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            printSQLException(e);
        }
        return rowDeleted;
    }


    private static void printSQLException(SQLException ex) {
        for (Throwable e:ex) {
            e.printStackTrace(System.err);
            System.err.println("SQLState: " + ((SQLException) e).getSQLState());
            System.err.println("Error Code " + ((SQLException) e).getErrorCode());
            System.err.println("Message: " + e.getMessage());
            Throwable t = ex.getCause();
            while (t != null) {
                System.out.println("Cause: " + t);
                t = t.getCause();
            }
        }
    }
}
