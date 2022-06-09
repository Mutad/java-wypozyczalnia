import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ThingController {
    private ArrayList<Thing> things;

    public ThingController() {
        try {
            loadThings();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void loadThings() throws FileNotFoundException {
        things = new ArrayList<>();
        Scanner listScanner = new Scanner(new File("ThingDatabase.csv"));
        while (listScanner.hasNextLine()) {
            things.add(Thing.Deserialize(listScanner.nextLine()));
        }
        listScanner.close();
    }

    public ArrayList<Thing> all() {
        return this.things;
    }

    public Thing find(int id) {
        for (Thing thing : things) {
            if (thing.id == id)
                return thing;
        }
        return null;
    }

    public void SaveThingsDatabase() throws FileNotFoundException {
        PrintWriter thingWriter = new PrintWriter(new File("ThingDatabase.csv"));
        for (Thing thing : things) {
            thingWriter.println(thing.id + "," + thing.name + "," + thing.price + "," + thing.owner);
        }
        thingWriter.close();
    }

    public void add(Thing thing) {
        things.add(thing);
    }

    public void remove(Thing thing) {
        things.remove(thing);
    }
}
