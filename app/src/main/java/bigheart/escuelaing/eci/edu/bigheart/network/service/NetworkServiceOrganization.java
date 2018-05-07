package bigheart.escuelaing.eci.edu.bigheart.network.service;


import bigheart.escuelaing.eci.edu.bigheart.model.Organization;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface NetworkServiceOrganization {

    @POST( "organization" )
    Call<Organization> createOrganization(@Body Organization o);

}