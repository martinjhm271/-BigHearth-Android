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

    @POST( "organization" )
    Call<Organization> createOrganization(@Body Organization o);

    @GET ("organization/{email}")
    Call<Organization> getOrganizationByEmail(@Path("email") String email);

    @GET ("organization/event/{id}")
    Call<Organization> getOrganizationByIdEvent(@Path("id") Integer id, RequestCallback<Organization> requestCallback);

    @GET ("organization/{nit}/events")
    Call<Event[]> getEvents(@Path("nit") Integer nit, RequestCallback<Event[]> requestCallback);

    @POST( "organization/modifyProfileOrg" )
    Call<Organization> updateOrganization(@Body Organization o, RequestCallback<Organization> requestCallback);

    @Multipart
    @POST( "organization/{email}/image/upload" )
    void setOrganizationImage(@Path("email") String email,@Part MultipartBody.Part m, RequestCallback<Organization> requestCallback);

    @GET ("organization/{email}/image")
    void getOrganizationImage(@Path("email") String email, RequestCallback<Organization> requestCallback);

}