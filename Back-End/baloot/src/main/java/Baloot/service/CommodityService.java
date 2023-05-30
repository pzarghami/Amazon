package Baloot.service;


import Baloot.domain.CommodityDomainManager;
import Baloot.model.DTO.*;
import Baloot.Exeption.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CommodityService {
    @RequestMapping(value = "/commodities", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getCommodities(@RequestParam(required = false) String filterBy, @RequestParam(required = false) String filterValue) {
        try {
            if(filterBy!=null && filterValue!=null)
                return new Response(true, "OK", getFilterCommodities(filterBy,filterValue));
            return new Response(true, "OK", CommodityDomainManager.getInstance().getCommodityDTOList());
        } catch (CustomException | SQLException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    private List<CommodityBriefDTO> getFilterCommodities(String filterBy, String filterValue) throws SQLException, CustomException {
        return switch (filterBy) {
            case "name" -> CommodityDomainManager.getInstance().getFilteredCommodityByName(filterValue);
            case "category" -> CommodityDomainManager.getInstance().getFilteredCommodityByCategory(filterValue);
            case "provider" -> CommodityDomainManager.getInstance().getFilteredCommodityByProvider(filterValue);
            default -> throw new CustomException("Invalid filter type");
        };
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
