package bigheart.escuelaing.eci.edu.bigheart.network.volunteer;

import bigheart.escuelaing.eci.edu.bigheart.model.Event;
import bigheart.escuelaing.eci.edu.bigheart.model.Organization;
import bigheart.escuelaing.eci.edu.bigheart.model.Volunteer;
import bigheart.escuelaing.eci.edu.bigheart.network.service.RequestCallback;
import okhttp3.MultipartBody.Part;


public interface NetworkVolunteer {
    void createVolunteer(Volunteer v, RequestCallback<Volunteer> requestCallback);
    void getVolunteerByEmail(String email, RequestCallback<Volunteer> requestCallback);
    void getVolunteerById(Integer id, RequestCallback<Volunteer> requestCallback);
    void getEvents(String email, RequestCallback<Event[]> requestCallback);
    void getEventsById(String id, RequestCallback<Volunteer> requestCallback);
    void updateVolunteer(Volunteer v, RequestCallback<Volunteer> requestCallback);
    void setVolunteerImage(String email, Part m, RequestCallback<Volunteer> requestCallback);
    void getVolunteerImage(String email, RequestCallback<Volunteer> requestCallback);
    void addSecureTokenInterceptor(String accessToken);

}
