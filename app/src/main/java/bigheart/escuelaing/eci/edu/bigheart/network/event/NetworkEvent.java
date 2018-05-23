package bigheart.escuelaing.eci.edu.bigheart.network.event;

import bigheart.escuelaing.eci.edu.bigheart.model.Event;
import bigheart.escuelaing.eci.edu.bigheart.network.login.LoginWrapper;
import bigheart.escuelaing.eci.edu.bigheart.network.login.Token;
import java.util.List;

import bigheart.escuelaing.eci.edu.bigheart.model.Event;
import bigheart.escuelaing.eci.edu.bigheart.network.service.RequestCallback;


public interface NetworkEvent {
    void createEvent(Event event,int NIT, RequestCallback<Event> requestCallback);

    public void getAllEvents(RequestCallback<List<Event>> requestCallback);
}
