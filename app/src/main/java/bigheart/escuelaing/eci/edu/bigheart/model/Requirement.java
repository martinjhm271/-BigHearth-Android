package bigheart.escuelaing.eci.edu.bigheart.model;




public class Requirement implements java.io.Serializable{

    private int id;
    private String name;
    private int quantity;
    private Event event;

    public Requirement() {
    }

    public Requirement(int id, String name, int quantity, Event event) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.event = event;
    }


    public int getId() { return id; }

    public void setId(int id) { this.id = id; }


    public String getName() { return name; }

    public void setName(String name) { this.name = name; }


    public int getQuantity() { return quantity; }

    public void setQuantity(int quality) { this.quantity = quality; }

    public Event getEvent() { return event; }

    public void setEvent(Event event) { this.event = event; }
}
