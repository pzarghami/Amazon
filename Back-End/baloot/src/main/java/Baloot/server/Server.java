package Baloot.server;


import Baloot.apiConsumer.CommodityApiConsumer;
import Baloot.apiConsumer.UserAPIConsumer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;

@SpringBootApplication
@ComponentScan(basePackages = "Baloot.service")
public class Server {
    public static void main(String[] args){

        try{
            (new UserAPIConsumer("http://5.253.25.110:5000/api/users")).importData();
            (new CommodityApiConsumer("http://5.253.25.110:5000/api/v2/commodities")).importData();
            throw new IOException();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
