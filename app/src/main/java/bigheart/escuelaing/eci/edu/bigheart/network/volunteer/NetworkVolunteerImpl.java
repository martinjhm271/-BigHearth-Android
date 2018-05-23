package bigheart.escuelaing.eci.edu.bigheart.network.volunteer;


import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import bigheart.escuelaing.eci.edu.bigheart.model.Event;
import bigheart.escuelaing.eci.edu.bigheart.model.Organization;

import bigheart.escuelaing.eci.edu.bigheart.model.Volunteer;
import bigheart.escuelaing.eci.edu.bigheart.network.service.NetworkException;
import bigheart.escuelaing.eci.edu.bigheart.network.service.NetworkServiceVolunteer;
import bigheart.escuelaing.eci.edu.bigheart.network.service.RequestCallback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.MultipartBody.Part;

public class NetworkVolunteerImpl implements NetworkVolunteer {

    private static final String BASE_URL = "http://104.211.48.12:8080/";
    private NetworkServiceVolunteer nsv;
    private ExecutorService backgroundExecutor = Executors.newFixedThreadPool( 1 );

    public NetworkVolunteerImpl() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl( BASE_URL ).addConverterFactory( GsonConverterFactory.create() ).build();
        nsv = retrofit.create( NetworkServiceVolunteer.class );
    }

    @Override
    public void createVolunteer(final Volunteer v,final RequestCallback<Volunteer> requestCallback) {

        backgroundExecutor.execute( new Runnable() {
            @Override
            public void run() {
                Call<Volunteer> call = nsv.createVolunteer(v);
                try {
                    Response<Volunteer> execute =call.execute();
                    requestCallback.onSuccess( execute.body() );
                }
                catch ( Exception e ) {
                    requestCallback.onFailed( new NetworkException( null, e ) );
                }
            }
        } );

    }

    @Override
    public void getVolunteerByEmail(String email, RequestCallback<Volunteer> requestCallback) {

    }

    @Override
    public void getVolunteerById(Integer id, RequestCallback<Volunteer> requestCallback) {

    }

    @Override
    public void getEvents(final String email, final RequestCallback<List<Event>> requestCallback) {
        backgroundExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Call<List<Event>> call = nsv.getEvents(email);
                try {
                    Response<List<Event>> execute =call.execute();
                    requestCallback.onSuccess(execute.body());
                }
                catch ( Exception e ) {
                    System.out.println(e.getMessage());
                    requestCallback.onFailed( new NetworkException( null, e ) );
                }
            }
        } );
    }

    @Override
    public void getEventsById(String id, RequestCallback<Volunteer> requestCallback) {

    }

    @Override
    public void updateVolunteer(Volunteer v, RequestCallback<Volunteer> requestCallback) {

    }

    @Override
    public void setVolunteerImage(final String email, final Part m, final RequestCallback<Volunteer> requestCallback) {

        backgroundExecutor.execute( new Runnable() {
            @Override
            public void run() {
                Call<Volunteer> call = nsv.setVolunteerImage(email,m);
                try {
                    Response<Volunteer> execute =call.execute();
                    requestCallback.onSuccess( execute.body() );
                }
                catch ( Exception e ) {
                    requestCallback.onFailed( new NetworkException( null, e ) );
                }
            }
        } );

    }

    @Override
    public void getVolunteerImage(String email, RequestCallback<Volunteer> requestCallback) {

    }

    @Override
    public void addSecureTokenInterceptor( final String token )
    {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor( new Interceptor()
        {
            @Override
            public okhttp3.Response intercept( Chain chain )
                    throws IOException
            {
                Request original = chain.request();

                Request request = original.newBuilder().header( "Accept", "application/json" ).header( "Authorization",
                        "Bearer "
                                + token ).method(
                        original.method(), original.body() ).build();
                return chain.proceed( request );
            }
        } );
        Retrofit retrofit = new Retrofit.Builder().baseUrl( BASE_URL ).addConverterFactory( GsonConverterFactory.create() ).client(httpClient.build() ).build();
        nsv = retrofit.create( NetworkServiceVolunteer.class );
    }
}