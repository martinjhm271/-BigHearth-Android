package bigheart.escuelaing.eci.edu.bigheart.network.organization;

import bigheart.escuelaing.eci.edu.bigheart.model.Organization;
import bigheart.escuelaing.eci.edu.bigheart.network.service.RequestCallback;


public interface NetworkOrganization {
    void createOrganization(Organization o, RequestCallback<Organization> requestCallback);
    void addSecureTokenInterceptor(String accessToken);
}
