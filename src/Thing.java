public class Thing {
    public int id;
    public String name;
    public double price;
    public int owner;

    public Thing(int id, String name, double price, int owner) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.owner = owner;
    }

    public static Thing Deserialize(String line) {
        String[] inputs = line.split(",");
        int id = Integer.parseInt(inputs[0]);
        String name = inputs[1];
        double price = Double.parseDouble(inputs[2]);
        int owner = Integer.parseInt(inputs[3]);
        return new Thing(id, name, price, owner);
    }
}
