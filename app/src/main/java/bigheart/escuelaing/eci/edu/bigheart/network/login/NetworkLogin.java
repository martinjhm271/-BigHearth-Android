package bigheart.escuelaing.eci.edu.bigheart.network.login;

import bigheart.escuelaing.eci.edu.bigheart.model.Credentials;
import bigheart.escuelaing.eci.edu.bigheart.network.service.RequestCallback;


public interface NetworkLogin {
    void login(Credentials loginWrapper, RequestCallback<Token> requestCallback);
    void addSecureTokenInterceptor(String accessToken);
}
