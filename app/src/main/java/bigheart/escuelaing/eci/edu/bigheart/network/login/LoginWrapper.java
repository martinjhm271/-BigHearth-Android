package bigheart.escuelaing.eci.edu.bigheart.network.login;



public class LoginWrapper {

    private final String username;

    private final String password;

    public LoginWrapper( String username, String password ) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}