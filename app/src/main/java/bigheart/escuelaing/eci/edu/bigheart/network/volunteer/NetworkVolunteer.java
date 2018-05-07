package bigheart.escuelaing.eci.edu.bigheart.network.volunteer;

import bigheart.escuelaing.eci.edu.bigheart.model.Volunteer;
import bigheart.escuelaing.eci.edu.bigheart.network.service.RequestCallback;


public interface NetworkVolunteer {
    void createVolunteer(Volunteer v, RequestCallback<Volunteer> requestCallback);
    void addSecureTokenInterceptor(String accessToken);

}
