package bigheart.escuelaing.eci.edu.bigheart.network.event;


import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import bigheart.escuelaing.eci.edu.bigheart.network.login.LoginWrapper;
import bigheart.escuelaing.eci.edu.bigheart.network.login.Token;
import bigheart.escuelaing.eci.edu.bigheart.network.service.NetworkException;
import bigheart.escuelaing.eci.edu.bigheart.network.service.NetworkServiceEvent;
import bigheart.escuelaing.eci.edu.bigheart.network.service.NetworkServiceLogin;
import bigheart.escuelaing.eci.edu.bigheart.network.service.NetworkServiceOrganization;
import bigheart.escuelaing.eci.edu.bigheart.network.service.RequestCallback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkEventImpl implements NetworkEvent {

    private static final String BASE_URL = "http://autenticationserver.herokuapp.com/";
    private NetworkServiceEvent nse;
    private ExecutorService backgroundExecutor = Executors.newFixedThreadPool( 1 );

    public NetworkEventImpl() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl( BASE_URL ).addConverterFactory( GsonConverterFactory.create() ).build();
        nse = retrofit.create( NetworkServiceEvent.class );
    }



}