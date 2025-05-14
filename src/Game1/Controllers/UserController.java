package Game1.Controllers;


import Game1.models.User;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserController {
    private static final String USER_FILE = "users.dat";
    private Map<String, User> users;

    public UserController() {
        loadUsers();
    }


    //读取用户
    @SuppressWarnings("unchecked")
    private void loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USER_FILE))) {
            users = (HashMap<String, User>) ois.readObject();
        } catch (FileNotFoundException e) {
            users = new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();                                    //？？？
            users = new HashMap<>();
        }
    }

    //写入用户
    private void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USER_FILE))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //注册
    public boolean register(String username, String password) {
        if (users.containsKey(username)) {
            return false;
        }

        users.put(username, new User(username, password));
        saveUsers();
        return true;
    }

    //登录
    public User login(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
