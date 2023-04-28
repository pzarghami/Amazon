package Baloot.server;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;

@SpringBootApplication
@ComponentScan(basePackages = "Baloot.service")
public class Server {
    public static void main(String[] args){
        try{
            throw new IOException();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
