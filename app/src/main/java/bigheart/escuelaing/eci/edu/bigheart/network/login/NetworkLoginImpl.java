package bigheart.escuelaing.eci.edu.bigheart.network.login;


import bigheart.escuelaing.eci.edu.bigheart.network.service.NetworkException;
import bigheart.escuelaing.eci.edu.bigheart.network.service.NetworkServiceLogin;
import bigheart.escuelaing.eci.edu.bigheart.network.service.RequestCallback;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkLoginImpl implements NetworkLogin {

    private static final String BASE_URL = "http://104.211.17.72:8080/";

    private NetworkServiceLogin nsl;
    private ExecutorService backgroundExecutor = Executors.newFixedThreadPool( 1 );

    public NetworkLoginImpl() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl( BASE_URL ).addConverterFactory( GsonConverterFactory.create() ).build();
        nsl = retrofit.create( NetworkServiceLogin.class );
    }

    @Override
    public void login(final LoginWrapper loginWrapper, final RequestCallback<Token> requestCallback ) {
        backgroundExecutor.execute( new Runnable() {
            @Override
            public void run() {
                Call<Token> call = nsl.login( loginWrapper );
                try {
                    Response<Token> execute = call.execute();
                    requestCallback.onSuccess( execute.body() );
                }
                catch ( IOException e ) {
                    requestCallback.onFailed( new NetworkException( null, e ) );
                }
            }
        } );

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
        nsl = retrofit.create( NetworkServiceLogin.class );
    }

}