package bigheart.escuelaing.eci.edu.bigheart.network.organization;


import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import bigheart.escuelaing.eci.edu.bigheart.model.Event;
import bigheart.escuelaing.eci.edu.bigheart.model.Organization;
import bigheart.escuelaing.eci.edu.bigheart.network.service.NetworkException;
import bigheart.escuelaing.eci.edu.bigheart.network.service.NetworkServiceOrganization;
import bigheart.escuelaing.eci.edu.bigheart.network.service.RequestCallback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.MultipartBody.Part;

public class NetworkOrganizationImpl implements NetworkOrganization {

    private static final String BASE_URL = "https://bighearth.herokuapp.com/";
    private NetworkServiceOrganization nso;
    private ExecutorService backgroundExecutor = Executors.newFixedThreadPool( 1 );

    public NetworkOrganizationImpl() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl( BASE_URL ).addConverterFactory( GsonConverterFactory.create() ).build();
        nso = retrofit.create( NetworkServiceOrganization.class );
    }


    @Override
    public void createOrganization(final Organization o, final RequestCallback<Organization> requestCallback) {

        backgroundExecutor.execute( new Runnable() {
            @Override
            public void run() {
                Call<Organization> call = nso.createOrganization(o);
                try {
                    Response<Organization> execute =call.execute();
                    requestCallback.onSuccess( execute.body() );
                }
                catch ( Exception e ) {
                    System.out.println(e.getMessage());
                    requestCallback.onFailed( new NetworkException( null, e ) );
                }
            }
        } );

    }

    @Override
    public void getOrganizationByEmail(String email, RequestCallback<Organization> requestCallback) {

    }

    @Override
    public void getOrganizationByIdEvent(Integer id, RequestCallback<Organization> requestCallback) {

    }

    @Override
    public void getEvents(Integer NIT, RequestCallback<Event[]> requestCallback) {

    }

    @Override
    public void updateOrganization(Organization o, RequestCallback<Organization> requestCallback) {

    }

    @Override
    public void setOrganizationImage(final String email, final Part m, final RequestCallback<Organization> requestCallback) {

        backgroundExecutor.execute( new Runnable() {
            @Override
            public void run() {
                Call<Organization> call = nso.setOrganizationImage(email,m);
                try {
                    Response<Organization> execute =call.execute();
                    requestCallback.onSuccess( execute.body() );
                }
                catch ( Exception e ) {
                    System.out.println(e.getMessage());
                    System.out.println(e.getStackTrace());
                    requestCallback.onFailed( new NetworkException( null, e ) );
                }
            }
        } );

    }

    @Override
    public void getOrganizationImage(String email, RequestCallback<Organization> requestCallback) {

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
        nso = retrofit.create( NetworkServiceOrganization.class );
    }
}