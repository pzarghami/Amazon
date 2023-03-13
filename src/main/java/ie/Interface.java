package ie;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Scanner;

public class Interface {
    public static void main(String[] args) throws JsonProcessingException {

        Baloot baloot = new Baloot();
        try {
            baloot.fetchData();
            baloot.startServer();
        }catch (CustomException e){
            e.printStackTrace();
        }
    }
}
