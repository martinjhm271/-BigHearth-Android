package bigheart.escuelaing.eci.edu.bigheart.network.service;

import bigheart.escuelaing.eci.edu.bigheart.model.Volunteer;
import bigheart.escuelaing.eci.edu.bigheart.network.login.LoginWrapper;
import bigheart.escuelaing.eci.edu.bigheart.network.login.Token;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface NetworkServiceVolunteer {

    @POST( "volunteer" )
    Call<Volunteer> createVolunteer(@Body Volunteer v);

}