package bigheart.escuelaing.eci.edu.bigheart.network.event;

import java.util.List;

import bigheart.escuelaing.eci.edu.bigheart.model.Event;
import bigheart.escuelaing.eci.edu.bigheart.network.service.RequestCallback;


public interface NetworkEvent {
    public void getAllEvents(RequestCallback<List<Event>> requestCallback);
}
