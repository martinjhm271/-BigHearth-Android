package bigheart.escuelaing.eci.edu.bigheart.network.event;

import bigheart.escuelaing.eci.edu.bigheart.model.Event;
import bigheart.escuelaing.eci.edu.bigheart.network.login.LoginWrapper;
import bigheart.escuelaing.eci.edu.bigheart.network.login.Token;
import java.util.List;

import bigheart.escuelaing.eci.edu.bigheart.model.Event;
import bigheart.escuelaing.eci.edu.bigheart.network.service.RequestCallback;
import okhttp3.MultipartBody.Part;

public interface NetworkEvent {
    void createEvent(Event event,int NIT, RequestCallback<Event> requestCallback);

    public void getAllEvents(RequestCallback<List<Event>> requestCallback);

    void getEventById(String idEvent, RequestCallback<Event> requestCallback);

    void setEventImage(String eventId, Part m, RequestCallback<Event> requestCallback);

    void unrol(String eventId,String email,RequestCallback<Boolean> requestCallback);
    void rol(String eventId,String email,RequestCallback<Boolean> requestCallback);
    void volunteerInEvent(String eventId,String email,RequestCallback<Boolean> requestCallback);
}
