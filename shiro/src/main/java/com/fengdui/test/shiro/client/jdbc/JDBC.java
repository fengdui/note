package com.fengdui.test.shiro.client.jdbc;

import java.sql.*;

/**
 * Created by fd on 2016/9/19.
 */
public class JDBC {
    public static final String url = "jdbc:mysql://127.0.0.1/e2e?serverTimezone=UTC&characterEncoding=utf8&useSSL=true";
    public static final String name = "com.mysql.cj.jdbc.Driver";
    public static final String user = "root";
    public static final String password = "root";

    public static void main(String[] args) {
        try {
            Class.forName(name);
            Connection connection1 = DriverManager.getConnection(url, user, password);
            connection1.setAutoCommit(false);
            Statement statement1 = connection1.createStatement();
            ResultSet set1 = statement1.executeQuery("select * from tab_no_index where id = 1 and name = '1' for update");
            while (set1.next()) {
                System.out.println(set1.getInt("id")+" "+set1.getString("name"));
            }
//            System.out.println(connection1.getTransactionIsolation());


            Connection connection2 = DriverManager.getConnection(url, user, password);
            connection2.setAutoCommit(false);
            Statement statement2 = connection2.createStatement();
            ResultSet set2 = statement2.executeQuery("select * from tab_no_index where id = 1 and name = '2' for update");
            while (set2.next()) {
                System.out.println(set2.getInt("id")+" "+set2.getString("name"));
            }

            connection1.close();
            connection2.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
        }
    }
}
