package bigheart.escuelaing.eci.edu.bigheart.network.service;

import bigheart.escuelaing.eci.edu.bigheart.model.Event;

import bigheart.escuelaing.eci.edu.bigheart.model.Volunteer;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import okhttp3.MultipartBody;
import retrofit2.http.Part;
import retrofit2.http.Multipart;


public interface NetworkServiceVolunteer {

    @POST( "volunteer" )
    Call<Volunteer> createVolunteer(@Body Volunteer v);

    @GET("volunteer/{email}")
    Call<Volunteer> getVolunteerByEmail(@Path("email") String email, RequestCallback<Volunteer> requestCallback);

    @GET ("volunteer/{email}/events")
    Call<Event[]> getEvents(@Path("email") String email, RequestCallback<Event[]> requestCallback);

    @POST( "volunteer/modifyProfileVol" )
    Call<Volunteer> updateVolunteer(@Body Volunteer v, RequestCallback<Volunteer> requestCallback);

    @Multipart
    @POST( "volunteer/{email}/image/upload" )
    void setVolunteerImage(@Path("email") String email, @Part MultipartBody.Part m, RequestCallback<Volunteer> requestCallback);

    @GET ("volunteer/{email}/image")
    void getVolunteerImage(@Path("email") String email, RequestCallback<Volunteer> requestCallback);

}