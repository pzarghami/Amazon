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

    @RequestMapping(value = "/user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getUserDTO() {
        return new Response(true, "OK", UserDomainManager.getInstance().getUserDTO());
    }

    @RequestMapping(value = "/auth/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response loginUser(@RequestBody String loginForm) {

        try {
            var loginJson = new ObjectMapper().readTree(loginForm);
            var username = loginJson.get("username").asText();
            var password = loginJson.get("password").asText();
            UserDomainManager.getInstance().loginUser(username, password);
            return new Response(true, "OK", username);
        } catch (CustomException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "InvalidCredential", e);
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/auth/logout", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response logoutUser() {
        UserDomainManager.getInstance().logoutUser();
        return new Response(true, "OK", "successfully logout");
    }

    @RequestMapping(value = "/auth/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response registerUser(@RequestBody String registerForm) {
        try {
            var registerJson = new ObjectMapper().readTree(registerForm);
            var username = registerJson.get("username").asText();
            var password = registerJson.get("password").asText();
            var email = registerJson.get("email").asText();
            var birthdate = registerJson.get("birthDate").asText();
            var address = registerJson.get("address").asText();
            UserDomainManager.getInstance().registerUser(username, password, email, birthdate, address);
            return new Response(true, "OK", "successfully register");
        } catch (CustomException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "InvalidCredential", e);
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/user/buylist/{commodityId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response addToBuyList(@PathVariable(value = "commodityId") int commodityId) {
        try {
            UserDomainManager.getInstance().addToBuyList(commodityId);
            return new Response(true, "OK", "successfully added");
        } catch (CustomException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "InvalidCredential", e);
        }
    }

    @RequestMapping(value = "/user/buylist/{commodityId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response removeFromBuyList(@PathVariable(value = "commodityId") int commodityId) {
        try {
            UserDomainManager.getInstance().removeFromBuyList(commodityId);
            return new Response(true, "OK", "successfully removed");
        } catch (CustomException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "InvalidCredential", e);
        }
    }

    @RequestMapping(value = "/addCredit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response addCredit(@RequestBody int amount) {
        UserDomainManager.getInstance().addCredit(amount);
        return new Response(true, "OK", "successfully add credit");
    }
}
