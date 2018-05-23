package bigheart.escuelaing.eci.edu.bigheart.network.login;



public class Token {

    private String accessToken;
    private String rol;

    public Token(){}

    public Token(String accessToken,String rol){
        this.rol=rol;
        this.accessToken=accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRol() {
        return rol;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}