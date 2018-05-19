package bigheart.escuelaing.eci.edu.bigheart.network.service;



public class NetworkException extends Exception{
    public NetworkException( String detailMessage, Throwable throwable ) {
        super( detailMessage, throwable );
    }
}
