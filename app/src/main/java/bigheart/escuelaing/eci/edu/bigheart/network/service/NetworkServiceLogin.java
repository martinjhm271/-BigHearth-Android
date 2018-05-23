package bigheart.escuelaing.eci.edu.bigheart.network.service;

import bigheart.escuelaing.eci.edu.bigheart.model.Credentials;
import bigheart.escuelaing.eci.edu.bigheart.network.login.LoginWrapper;
import bigheart.escuelaing.eci.edu.bigheart.network.login.Token;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface NetworkServiceLogin {

    @POST( "login/login" )
    Call<Token> login(@Body Credentials user);

}