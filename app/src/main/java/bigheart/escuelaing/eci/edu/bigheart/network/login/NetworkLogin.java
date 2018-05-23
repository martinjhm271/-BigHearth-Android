package bigheart.escuelaing.eci.edu.bigheart.network.login;

import bigheart.escuelaing.eci.edu.bigheart.network.service.RequestCallback;


public interface NetworkLogin {
    void login(LoginWrapper loginWrapper, RequestCallback<Token> requestCallback);
    void addSecureTokenInterceptor(String accessToken);
}
