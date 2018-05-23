package bigheart.escuelaing.eci.edu.bigheart.network.organization;

import java.util.List;

import bigheart.escuelaing.eci.edu.bigheart.model.Organization;
import bigheart.escuelaing.eci.edu.bigheart.model.Event;
import bigheart.escuelaing.eci.edu.bigheart.network.service.RequestCallback;
import okhttp3.MultipartBody.Part;


public interface NetworkOrganization {
    void createOrganization(Organization o, RequestCallback<Organization> requestCallback);
    void getOrganizationByEmail(String email, RequestCallback<Organization> requestCallback);
    void getOrganizationByIdEvent(Integer id, RequestCallback<Organization> requestCallback);
    void getEvents(Integer NIT, RequestCallback<List<Event>> requestCallback);
    void updateOrganization(Organization o, RequestCallback<Organization> requestCallback);
    void setOrganizationImage(String email, Part m, RequestCallback<Organization> requestCallback);
    void getOrganizationImage(String email, RequestCallback<Organization> requestCallback);
    void addSecureTokenInterceptor(String accessToken);
}
