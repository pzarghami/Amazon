import java.util.*;

// Main class
class GFG {

    // Main driver method
    public static void main(String[] args)
    {

        // creating an empty HashMap of string and integer
        // pairs Mappings denotes Student name and marks
        HashMap<String, Integer> hm
                = new HashMap<String, Integer>();

        // Adding mappings to HashMap
        // using put() method
        hm.put("GeeksforGeeks", 54);
        hm.put("A computer portal", 80);
        hm.put("For geeks", 82);

        // Printing all elements of above Map
        System.out.println("Created hashmap is" + hm);

        // Display message only
        System.out.println(
                "HashMap after adding bonus marks:");

        // Looping through the HashMap
        // Using for-each loop
        for (Map.Entry<String,Integer> mapElement : hm.entrySet()) {
            String key = mapElement.getKey();

            // Adding some bonus marks to all the students


            // Printing above marks corresponding to
            // students names
            System.out.println(key);
        }
    }
}