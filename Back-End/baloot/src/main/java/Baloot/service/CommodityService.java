package Baloot.service;


import Baloot.domain.CommodityDomainManager;
import Baloot.model.DTO.*;
import Baloot.Exeption.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CommodityService {


    @RequestMapping(value = "/commodities/{id}", method = RequestMethod.GET, produces =  MediaType.APPLICATION_JSON_VALUE)
    public Response getCommodityInfo(@PathVariable(value = "id") String commodityId){
        try{
            var test = CommodityDomainManager.getInstance().getCommodityDTO(commodityId);
            System.out.println(test);
            return new Response(true,"OK", CommodityDomainManager.getInstance().getCommodityDTO(commodityId));
        }catch (CustomException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
