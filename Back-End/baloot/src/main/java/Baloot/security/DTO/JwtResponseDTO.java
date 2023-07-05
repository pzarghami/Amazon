package Baloot.security.DTO;

public class JwtResponseDTO {
    private String username;
    private String jwt;

    public JwtResponseDTO(String userEmail, String jwt) {
        this.username = userEmail;
        this.jwt = jwt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
