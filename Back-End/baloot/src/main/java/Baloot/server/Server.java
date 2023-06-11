package Baloot.server;

import Baloot.Exeption.CustomException;
import Baloot.apiConsumer.CommodityApiConsumer;
import Baloot.apiConsumer.DiscountApiConsumer;
import Baloot.apiConsumer.ProviderApiConsumer;
import Baloot.apiConsumer.UserAPIConsumer;
import Baloot.model.Provider;
import Baloot.repository.CommodityRepo;
import Baloot.repository.ProviderRepo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@ComponentScan(basePackages = "Baloot.service")
public class Server {
    public static void main(String[] args) {

        try {
            (new UserAPIConsumer("http://5.253.25.110:5000/api/users")).importData();
            (new ProviderApiConsumer("http://5.253.25.110:5000/api/v2/providers")).importData();
            (new CommodityApiConsumer("http://5.253.25.110:5000/api/v2/commodities")).importData();
            (new DiscountApiConsumer("http://5.253.25.110:5000/api/discount")).importData();


        } catch (IOException e) {
            e.printStackTrace();
        }
        SpringApplication.run(Server.class, args);
    }
}
