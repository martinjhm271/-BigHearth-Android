package bigheart.escuelaing.eci.edu.bigheart.network.service;

import bigheart.escuelaing.eci.edu.bigheart.model.Event;
import bigheart.escuelaing.eci.edu.bigheart.network.login.LoginWrapper;
import bigheart.escuelaing.eci.edu.bigheart.network.login.Token;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface NetworkServiceEvent {

    @POST( "event/createEvent/{NIT}" )
    Call<Event> createEvent(@Body Event e,@Path("NIT") int NIT);

}