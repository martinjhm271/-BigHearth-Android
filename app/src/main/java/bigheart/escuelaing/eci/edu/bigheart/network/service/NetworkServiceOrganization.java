package bigheart.escuelaing.eci.edu.bigheart.network.service;


import bigheart.escuelaing.eci.edu.bigheart.model.Event;
import bigheart.escuelaing.eci.edu.bigheart.model.Organization;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import okhttp3.MultipartBody;
import retrofit2.http.Part;
import retrofit2.http.Multipart;


public interface NetworkServiceOrganization {

    @POST("organization")
    Call<Organization> createOrganization(@Body Organization o);

    @GET ("organization/{email}")
    Call<Organization> getOrganizationByEmail(@Path("email") String email);

    @GET ("organization/event/{id}")
    Call<Organization> getOrganizationByIdEvent(@Path("id") Integer id);

    @GET ("organization/{nit}/events")
    Call<Event[]> getEvents(@Path("nit") Integer nit);

    @POST( "organization/modifyProfileOrg" )
    Call<Organization> updateOrganization(@Body Organization o);

    @Multipart
    @POST( "organization/{email}/image/upload" )
    Call<Organization> setOrganizationImage(@Path("email") String email,@Part MultipartBody.Part m);

    @GET ("organization/{email}/image")
    void getOrganizationImage(@Path("email") String email);

}