package bigheart.escuelaing.eci.edu.bigheart.network.service;


import bigheart.escuelaing.eci.edu.bigheart.network.service.NetworkException;

public interface RequestCallback<T> {

    void onSuccess(T response);

    void onFailed(NetworkException e);

}