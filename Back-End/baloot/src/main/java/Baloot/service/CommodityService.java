package Baloot.service;


import Baloot.domain.CommodityDomainManager;
import Baloot.model.DTO.*;
import Baloot.Exeption.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CommodityService {
    @RequestMapping(value = "/commodities", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getCommodities() {
        try {
            return new Response(true, "OK", CommodityDomainManager.getInstance().getCommodityDTOList());
        } catch (CustomException | SQLException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/commodities/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getCommodityInfo(@PathVariable(value = "id") String commodityId) {
        try {
            return new Response(true, "OK", CommodityDomainManager.getInstance().getCommodityDTO(commodityId));
        } catch (CustomException | SQLException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/commodities/{id}/rate/{data}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response postCommodityRate(@PathVariable(value = "id") String commodityId, @PathVariable(value = "data") int rate) {
        try {
            return new Response(true, "OK", CommodityDomainManager.getInstance().rateCommodity(commodityId, rate));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

}
