import java.util.Scanner;

public class AdminUser extends User {
    public AdminUser(int id, String nickname, double money, String address) {
        super(id, nickname, money, address, true);
    }

    @Override
    public Boolean Login() {
        System.out.println("Enter password: ");
        String input = (new Scanner(System.in)).nextLine();
        if (input.equals("admin")) {
            System.out.println("You logged in successfully as admin!");
            return true;
        }
        System.out.println("Wrong password!");
        return false;
    }
}
