package Baloot.service;

import Baloot.Exeption.CustomException;
import Baloot.domain.ProviderDomainManager;
import Baloot.model.DTO.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProviderService {
    @RequestMapping(value = "/providers/{id}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getProviderDTO(@PathVariable(value = "id")String providerID){
        try{
            return new Response(true,"OK", ProviderDomainManager.getInstance().getProviderDTO(providerID));
        }catch (CustomException | SQLException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Actor Not Found", e);
        }
    }
}
