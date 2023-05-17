package Baloot.service;


import Baloot.domain.CommodityDomainManager;
import Baloot.model.DTO.*;
import Baloot.Exeption.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.web.server.ResponseStatusException;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class CommodityService {

    @RequestMapping(value = "/commodities/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getCommodityInfo(@PathVariable(value = "id") String commodityId){
        try{
            var test = CommodityDomainManager.getInstance().getCommodityDTO(commodityId);
            System.out.println(test);
            return new Response(true,"OK", CommodityDomainManager.getInstance().getCommodityDTO(commodityId));
        }catch (CustomException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/commodities/{id}/rate/{data}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response postCommodityRate(@PathVariable(value = "id") String commodityId,@PathVariable(value = "data") int rate){
        try {
            return new Response(true, "OK", CommodityDomainManager.getInstance().rateCommodity(commodityId,rate));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
