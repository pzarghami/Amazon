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

import java.lang.reflect.Array;
import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserService {

    @RequestMapping(value = "/user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getUserDTO() {
        try {
            return new Response(true, "OK", UserDomainManager.getInstance().getUserDTO());
        } catch (CustomException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
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
            return new Response(true, "OK", UserDomainManager.getInstance().addToBuyList(commodityId));
        } catch (CustomException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "InvalidCredential", e);
        }
    }

    @RequestMapping(value = "/user/buylist/{commodityId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response removeFromBuyList(@PathVariable(value = "commodityId") int commodityId) {
        try {
            return new Response(true, "OK", UserDomainManager.getInstance().removeFromBuyList(commodityId));
        } catch (CustomException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "InvalidCredential", e);
        }
    }

    @RequestMapping(value = "/addCredit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response addCredit(@RequestBody String amountForm) {
        try {
            var registerJson = new ObjectMapper().readTree(amountForm);
            var amount = registerJson.get("amount").asInt();
            UserDomainManager.getInstance().addCredit(amount);
            return new Response(true, "OK", "successfully add credit");
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/price", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getPrice() {
        return new Response(true, "OK", UserDomainManager.getInstance().getPrice());
    }

    @RequestMapping(value = "/discount", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response setDiscount(@RequestBody String discountForm) {
        try {
            var discountJson = new ObjectMapper().readTree(discountForm);
            var discountCode = discountJson.get("discountCode").asText();
            return new Response(true, "OK", UserDomainManager.getInstance().setDiscount(discountCode));
        } catch (CustomException | JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/discount", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response removeDiscount() {
        UserDomainManager.getInstance().removeDiscount();
        return new Response(true, "OK", "discount successfully removed");
    }

    @RequestMapping(value = "/pay", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response payNow() {
        try {
            return new Response(true, "OK", UserDomainManager.getInstance().finalizeThePurchase());
        } catch (CustomException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "InvalidCredential", e);
        }
    }

    @RequestMapping(value = "/buyListSize", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getBuyListSize() {
        return new Response(true, "OK", UserDomainManager.getInstance().getBuyListSize());
    }
}
