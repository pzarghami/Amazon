package ie;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Scanner;

public class Interface {
    public static void main(String[] args) throws JsonProcessingException {

        Baloot baloot = new Baloot();
        Scanner terminal = new Scanner(System.in);
        String line;
        while ((line = terminal.nextLine()) != null) {
            String[] input = line.split(" ", 2);
            String command = input[0];
            String data;
            if (input.length == 1) {
                data = "";
            } else {
                data = input[1];
            }
            baloot.RunCommand(command, data);
        }
        terminal.close();

    }
}
