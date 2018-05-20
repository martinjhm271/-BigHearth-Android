package bigheart.escuelaing.eci.edu.bigheart.model;


import java.util.List;




public class Organization implements java.io.Serializable{




    private String commercialName;


    private String businessName;

    private String state;

    private String city;


    private String address;

    private String description;


    private RolUser mail;


    private byte[] photo;


    private String password;


    private int nit;


    private int volunteersMade;


    private List<Event> myEvents;

    public Organization() {
    }

    public Organization(String commercialName, String businessName, String state, String city, String address, String description, RolUser mail, byte[] photo, String password, int nit, int volunteersMade, List<Event> myEvents) {
        this.commercialName = commercialName;
        this.businessName = businessName;
        this.state = state;
        this.city = city;
        this.address = address;
        this.description = description;
        this.mail = mail;
        this.photo = photo;
        this.password = password;
        this.nit = nit;
        this.volunteersMade = volunteersMade;
        this.myEvents = myEvents;
    }

    public String getCommercialName() {
        return commercialName;
    }

    public void setCommercialName(String commercialName) {
        this.commercialName = commercialName;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RolUser getMail() {
        return mail;
    }

    public void setMail(RolUser mail) {
        this.mail = mail;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getNit() {
        return nit;
    }

    public void setNit(int nit) {
        this.nit = nit;
    }

    public int getVolunteersMade() {
        return volunteersMade;
    }

    public void setVolunteersMade(int volunteersMade) {
        this.volunteersMade = volunteersMade;
    }


    public List<Event> getMyEvents() { return myEvents; }

    public void setMyEvents(List<Event> myEvents) { this.myEvents = myEvents; }

    @Override
    public String toString(){
        return "[Organization -> commercialName: "+commercialName+", businessName: "+businessName+", state: "+state+", city: "+city+", address: "+address+", mail: "+mail.toString()+", password: "+password+", photo: "+photo+", NIT: "+Integer.toString(nit)+", volunteersMade: "+Integer.toString(volunteersMade)+"]";
    }
}