package net.smgui.entity;

/**
 * User
 *
 * @author FD
 * @date 2016/2/14 0014
 */
public class User {

    private int id;
    private String username;
    private String password;

    public User(int id){
        this.id = id;
    }

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public User(){}

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}