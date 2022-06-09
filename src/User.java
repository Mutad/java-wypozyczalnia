public class User {
    public boolean isAdmin;
    public int id;
    public String nickname;
    public double money;
    public String address;

    public User(int id, String nickname, double money, String address, boolean isAdmin) {
        this.id = id;
        this.nickname = nickname;
        this.money = money;
        this.address = address;
        this.isAdmin = isAdmin;
    }

    public static User Deserialize(String line) {
        String[] inputs = line.split(",");
        int id = Integer.parseInt(inputs[0]);
        String nickname = inputs[1];
        double money = Double.parseDouble(inputs[2]);
        String address = inputs[3];
        boolean isAdmin = Boolean.parseBoolean(inputs[4]);
        if (isAdmin)
            return new AdminUser(id, nickname, money, address);
        return new User(id, nickname, money, address, false);
    }

    public Boolean Login() {
        System.out.println("Logged in");
        return true;
    }

    public String Serialize() {
        return id + "," + nickname + "," + money + "," + address + "," + isAdmin;
    }
}
