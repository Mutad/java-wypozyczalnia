import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private User user = null;
    private final UserController userController;
    private final ThingController thingController;

    public Menu() {
        userController = new UserController();
        thingController = new ThingController();

        while (user == null) {
            user = authenticateUser();
            if (user == null) {
                System.out.println("User not found in our database or password is invalid!");
            }
        }

        displayMainMenu();

        try {
            userController.SaveUserDatabase();
            thingController.SaveThingsDatabase();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void displayMainMenu() {
        System.out.println("Main menu");
        while (true) {
            System.out.println("[1] Show Things List");
            System.out.println("[2] Show my profile");
            System.out.println("[3] Rent something");
            System.out.println("[4] Return something");
            if (user.isAdmin)
                System.out.println("[5] Admin menu");
            System.out.println("[0] Log out");
            Scanner inputScanner = new Scanner(System.in);
            int option = inputScanner.nextInt();
            switch (option) {
                case 1:
                    ShowThingList();
                    break;
                case 2:
                    ShowUserProfile();
                    break;
                case 3:
                    UserRenting();
                    break;
                case 4:
                    UserReturn();
                    break;
                case 5:
                    if (user.isAdmin)
                        AdminMenu();
                    else
                        System.out.println("Wrong input!");
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Wrong input!");
                    break;
            }

        }
    }

    void ShowThingList() {
        for (Thing thing : thingController.all()) {
            System.out.println("ID: " + thing.id);
            System.out.println("Name: " + thing.name);
            System.out.println("Price: " + thing.price);
            System.out.println("Owner ID: " + thing.owner + "\n");
        }
    }

    void ShowUserList() {
        for (User user : userController.all()) {
            System.out.println("ID: " + user.id);
            System.out.println("Nickname: " + user.nickname);
            System.out.println("Money: " + user.money);
            System.out.println("Address: " + user.address + "\n");
        }
    }

    void ShowRentedThingList() {
        for (Thing thing : thingController.all()) {
            if (thing.owner != 0) {
                System.out.println("ID: " + thing.id);
                System.out.println("Name: " + thing.name);
                System.out.println("Price: " + thing.price);
                System.out.println("Owner ID: " + thing.owner + "\n");
            }
        }
    }


    int UserIdGenerator() {
        int idPicker = 1;
        boolean isChanged = true;
        while (isChanged) {
            isChanged = false;
            for (User user : userController.all()) {
                if (user.id == idPicker) {
                    idPicker++;
                    isChanged = true;
                    break;
                }
            }
        }
        return idPicker;
    }

    int ThingIdGenerator() {
        int idPicker = 1;
        boolean isChanged = true;
        while (isChanged) {
            isChanged = false;
            for (Thing thing : thingController.all()) {
                if (thing.id == idPicker) {
                    idPicker++;
                    isChanged = true;
                    break;
                }
            }
        }
        return idPicker;
    }


    void AddUser() {
        System.out.println("Give the parameters of user:\n");
        int id = UserIdGenerator();
        System.out.println("Nickname: ");
        Scanner inputScanner = new Scanner(System.in);
        String nickname = inputScanner.nextLine();
        System.out.println(nickname);
        System.out.println("Money: ");
        double money = inputScanner.nextDouble();
        inputScanner.nextLine();
        System.out.println(money);
        System.out.println("Address: ");
        String address = inputScanner.nextLine();
        System.out.println(address);
        User user = new User(id, nickname, money, address, false);
        userController.add(user);
        System.out.println("User was successfully added!");
    }

    void EditUser() {
        System.out.println("User ID: ");
        User user = userController.find(new Scanner(System.in).nextInt());
        System.out.println("What do you want to change?");
        while (true) {
            System.out.println("[1] Nickname: " + user.nickname);
            System.out.println("[2] Money: " + user.money);
            System.out.println("[3] Address: " + user.address);
            System.out.println("[4] Save");
            Scanner inputScanner = new Scanner(System.in);
            int input = inputScanner.nextInt();
            switch (input) {
                case 1:
                    System.out.println("New nickname: ");
                    user.nickname = inputScanner.nextLine();
                    System.out.println("Nickname was successfully changed!");
                    break;
                case 2:
                    System.out.println("New amount of money: ");
                    user.money = Double.parseDouble(inputScanner.nextLine());
                    System.out.println("Amount of money was successfully changed!");
                    break;
                case 3:
                    System.out.println("New address: ");
                    user.address = inputScanner.nextLine();
                    System.out.println("Address was successfully changed!");
                    break;
                case 4:
                    System.out.println("User was successfully saved!");
                    return;
                default:
                    System.out.println("Wrong input!");
            }
        }
    }

    void RemoveUser() {
        System.out.println("User ID: ");
        User user = userController.find(new Scanner(System.in).nextInt());
        if (user == this.user)
        {
            System.out.println("You cannot remove yourself");
            return;
        }
        userController.remove(user);
        for (Thing thing : thingController.all()) {
            if (user.id == thing.owner) {
                thing.owner = 0;
            }
        }
        System.out.println("User was successfully removed!");
    }

    void ShowUserProfile() {
        System.out.println("ID: " + user.id);
        System.out.println("Nickname: " + user.nickname);
        System.out.println("Money: " + user.money);
        System.out.println("Address: " + user.address + "\n");
        System.out.println("Things you rented: ");
        for (Thing thing : thingController.all()) {
            if (thing.owner == user.id) {
                System.out.println(thing.name + " - " + thing.price + "$");
            }
        }
        System.out.println();
    }

    void UserRenting() {
        while (true) {
            System.out.println("What do you want to rent?");
            for (Thing thing : thingController.all()) {
                if (user.money >= thing.price && thing.owner == 0) {
                    System.out.println("[" + thing.id + "] " + thing.name + " - " + thing.price + "$");
                }
            }
            System.out.println("[0] Go back");
            Scanner inputScanner = new Scanner(System.in);
            int input = inputScanner.nextInt();
            if (input == 0)
                return;
            Thing thing = thingController.find(input);
            if (thing == null)
                System.out.println("Wrong input!");
            else {
                thing.owner = user.id;
                user.money -= thing.price;
                for (User u : userController.all()) {
                    if (u.id == user.id) {
                        u.money = user.money;
                        break;
                    }
                }
                System.out.println("You successfully rented " + thing.name + "!");
                return;
            }

        }
    }


    void AddThing() {
        System.out.println("Give the parameters of thing:\n");
        int id = ThingIdGenerator();
        System.out.println("Name: ");
        Scanner inputScanner = new Scanner(System.in);
        String name = inputScanner.nextLine();
        System.out.println("Price: ");
        double price = inputScanner.nextDouble();
        System.out.println("Owner ID: ");
        User owner = userController.find(new Scanner(System.in).nextInt());
        Thing thing = new Thing(id, name, price, owner.id);
        thingController.add(thing);
        System.out.println("Thing was successfully added!");
    }

    void EditThing() {
        System.out.println("Thing ID: ");
        Thing thing = thingController.find(new Scanner(System.in).nextInt());
        System.out.println("What do you want to change?");
        while (true) {
            System.out.println("[1] Name: " + thing.name);
            System.out.println("[2] Price: " + thing.price);
            System.out.println("[3] Owner: " + thing.owner);
            System.out.println("[4] Save");
            Scanner inputScanner = new Scanner(System.in);
            int input = inputScanner.nextInt();
            switch (input) {
                case 1:
                    System.out.println("New name: ");
                    thing.name = inputScanner.nextLine();
                    System.out.println("Name was successfully changed!");
                    break;
                case 2:
                    System.out.println("New price: ");
                    thing.price = Double.parseDouble(inputScanner.nextLine());
                    System.out.println("Price was successfully changed!");
                    break;
                case 3:
                    System.out.println("New owner: ");
                    User owner = userController.find(new Scanner(System.in).nextInt());
                    thing.owner = owner.id;
                    System.out.println("Owner was successfully changed!");
                    break;
                case 4:
                    System.out.println("Thing was successfully saved!");
                    return;
                default:
                    System.out.println("Wrong input!");
                    break;
            }
        }
    }

    void RemoveThing() {
        System.out.println("Thing ID: ");
        Thing thing = thingController.find(new Scanner(System.in).nextInt());
        thingController.remove(thing);
        System.out.println("Thing was successfully removed!");
    }


    void AdminMenu() {
        System.out.println("What do you want to do?");
        while (true) {
            System.out.println("[1] Add User");
            System.out.println("[2] Edit User");
            System.out.println("[3] Remove User");
            System.out.println("[4] Add Thing");
            System.out.println("[5] Edit Thing");
            System.out.println("[6] Remove Thing");
            System.out.println("[7] Show Users List");
            System.out.println("[8] Show Things List");
            System.out.println("[9] Show Rented Things List");
            System.out.println("[0] Back to main menu");
            Scanner inputScanner = new Scanner(System.in);
            int input = inputScanner.nextInt();

            switch (input) {
                case 1:
                    AddUser();
                    break;
                case 2:
                    EditUser();
                    break;
                case 3:
                    RemoveUser();
                    break;
                case 4:
                    AddThing();
                    break;
                case 5:
                    EditThing();
                    break;
                case 6:
                    RemoveThing();
                    break;
                case 7:
                    ShowUserList();
                    break;
                case 8:
                    ShowThingList();
                    break;
                case 9:
                    ShowRentedThingList();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Wrong input");
            }
        }
    }

    void UserReturn() {
        System.out.println("What do you want to return?");
        while (true) {
            System.out.println("Things you rented: ");
            for (Thing thing : thingController.all()) {
                if (thing.owner == user.id) {
                    System.out.println("[" + thing.id + "] " + thing.name);
                }
            }
            System.out.println("[0] Go back");
            Scanner inputScanner = new Scanner(System.in);
            int input = inputScanner.nextInt();
            if (input == 0)
                return;

            Thing thing = thingController.find(input);

            if (thing == null) {
                System.out.println("Item with that id is not found");
            } else {
                thing.owner = 0;
                System.out.println("You successfully returned " + thing.name + "!");
                return;
            }
        }

    }

    private User authenticateUser() {
        System.out.println("Hello!");
        while (true) {
            System.out.println("[1] Start");
            System.out.println("[0] Quit");
            Scanner inputScanner = new Scanner(System.in);
            int option = inputScanner.nextInt();
            switch (option) {
                case 1:
                    return userController.authenticate(AskForUsername());
                case 0:
                    return null;
            }
            System.out.println("Wrong input!");
        }
    }

    private String AskForUsername() {
        System.out.println("Please, enter your username: ");
        return (new Scanner(System.in)).nextLine();
    }
}
