import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class UserController {
    private ArrayList<User> users;

    public UserController() {
        try {
            loadUsers();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void loadUsers() throws FileNotFoundException {
        users = new ArrayList<>();
        Scanner listScanner = new Scanner(new File("UserDatabase.csv"));
        while (listScanner.hasNextLine()) {
            users.add(User.Deserialize(listScanner.nextLine()));
        }
        listScanner.close();
    }

    public ArrayList<User> all() {
        return this.users;
    }

    public User find(int id) {
        for (User user : users) {
            if (user.id == id)
                return user;
        }
        return null;
    }

    public User authenticate(String username){
        for (User user : users) {
            if (user.nickname.equals(username)){
                if (user.Login())
                    return user;
            }
        }
        return null;
    }

    public void SaveUserDatabase() throws FileNotFoundException {
        PrintWriter userWriter = new PrintWriter(new File("UserDatabase.csv"));
        for (User user : users) {
            userWriter.println(user.Serialize());
        }
        userWriter.close();
    }

    public void add(User user) {
        users.add(user);
    }

    public void remove(User user) {
        users.remove(user);
    }
}
