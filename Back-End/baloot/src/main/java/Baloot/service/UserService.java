package Baloot.service;

import Baloot.Exeption.CustomException;
import Baloot.domain.UserDomainManager;
import Baloot.model.DTO.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserService {
    @RequestMapping(value = "/auth/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response loginUser(@RequestBody String loginForm){
        try{
            var loginJson = new ObjectMapper().readTree(loginForm);
            var username = loginJson.get("username").asText();
            var password = loginJson.get("password").asText();
            UserDomainManager.getInstance().loginUser(username,password);
            return new Response(true, "OK", username);
        }catch (CustomException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "InvalidCredential", e);
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
