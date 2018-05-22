package bigheart.escuelaing.eci.edu.bigheart.network.service;

import java.util.List;

import bigheart.escuelaing.eci.edu.bigheart.model.Event;
import bigheart.escuelaing.eci.edu.bigheart.network.login.LoginWrapper;
import bigheart.escuelaing.eci.edu.bigheart.network.login.Token;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface NetworkServiceEvent {

    @POST( "event" )
    Call<Token> createEvent(@Body Event e);

    @GET(" event/AllEvent ")
    Call<List<Event>> getAllEvents();

}