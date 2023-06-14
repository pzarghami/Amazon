package Baloot.security.DTO;

public class JwtResponseDTO {
    private String userEmail;
    private String jwt;

    public JwtResponseDTO(String userEmail, String jwt) {
        this.userEmail = userEmail;
        this.jwt = jwt;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
