package Baloot.service;

import Baloot.Exeption.CustomException;
import Baloot.domain.UserDomainManager;
import Baloot.model.DTO.Response;
import Baloot.model.DTO.UserDTO;
import Baloot.security.DTO.JwtRequestDTO;
import Baloot.security.DTO.JwtResponseDTO;
import Baloot.security.JwtTokenUtil;
import Baloot.util.ApiClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@ComponentScan(basePackages ={"ie.iemdb.security", "ie.iemdb.util"})
public class UserService {

    private final JwtTokenUtil jwtTokenUtil=new JwtTokenUtil();
    private final ApiClient apiClient=new ApiClient();
    @Value("${Oauth.client-secret}")
    private String oauthClientSecret;
    @Value("${Oauth.client-id}")
    private String oauthClientId;
    private final ObjectMapper mapper = new ObjectMapper();
    @RequestMapping(value = "/auth/oauth-login", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response loginWithOauth(@RequestParam(value="code", required = true) String code) throws SQLException {
        try {
            var accessTokenUrl = String.format("https://github.com/login/oauth/access_token?client_id=%s&client_secret=%s&code=%s",
                    oauthClientId, oauthClientSecret, code);
            var accessTokenCallResult = mapper.readTree(apiClient.post(accessTokenUrl, "Accept", "application/json"));
            var userInfoCallResult = apiClient.get("https://api.github.com/user",
                    "Authorization",String.format("token %s", accessTokenCallResult.get("access_token").asText()));
            return authenticateUser(userInfoCallResult);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "SomethingWentWrong", e);
        } catch (CustomException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
    private Response authenticateUser(String userInfoCallResult) throws JsonProcessingException, SQLException, CustomException {
        var jsonResult = mapper.readTree(userInfoCallResult);
        var userDTO = new UserDTO();
        userDTO.setNickname(jsonResult.get("login").asText());
        userDTO.setEmail(jsonResult.get("email").asText());
        userDTO.setUsername(jsonResult.get("name").asText());
        userDTO.setAddress(jsonResult.get("address").asText());
        userDTO.setPassword(null);
        userDTO.setBirthDate(LocalDate.parse(jsonResult.get("created_at").asText().split("T")[0]).minusYears(18).toString());

        var username = userDTO.getUsername();
        UserDomainManager.getInstance().registerOrLoinUser(userDTO);
        return new Response(true, "OK", new JwtResponseDTO(username, jwtTokenUtil.generateToken(username)));
    }
    @RequestMapping(value = "/user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getUserDTO() {
        try {
            return new Response(true, "OK", UserDomainManager.getInstance().getUserDTO());
        } catch (CustomException | SQLException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/auth/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response loginUser(@RequestBody JwtRequestDTO loginForm) {

        try {
            if(!loginForm.checkNullability()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            var userEmail = loginForm.getEmail();
            var userPassword = loginForm.getPassword();
            UserDomainManager.getInstance().loginUser(userEmail, userPassword);
            return new Response(true, "okeb", new JwtResponseDTO(userEmail, jwtTokenUtil.generateToken(userEmail)));
        } catch (CustomException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "InvalidCredential", e);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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
        } catch (CustomException | SQLException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "InvalidCredential", e);
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/user/buylist/{commodityId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response addToBuyList(@PathVariable(value = "commodityId") int commodityId) {
        try {
            return new Response(true, "OK", UserDomainManager.getInstance().addToBuyList(commodityId));
        } catch (CustomException | SQLException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "InvalidCredential", e);
        }
    }

    @RequestMapping(value = "/user/buylist/{commodityId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response removeFromBuyList(@PathVariable(value = "commodityId") int commodityId) {
        try {
            return new Response(true, "OK", UserDomainManager.getInstance().removeFromBuyList(commodityId));
        } catch (CustomException | SQLException e) {
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
        } catch (CustomException | JsonProcessingException | SQLException e) {
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
        } catch (CustomException | SQLException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "InvalidCredential", e);
        }
    }

    @RequestMapping(value = "/buyListSize", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getBuyListSize() {
        return new Response(true, "OK", UserDomainManager.getInstance().getBuyListSize());
    }
}
