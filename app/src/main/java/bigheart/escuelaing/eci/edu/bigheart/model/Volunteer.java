package bigheart.escuelaing.eci.edu.bigheart.model;


import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class Volunteer  implements Serializable {




    private Integer vol_id;


    private String name;

    private String lastname;


    private String gender;


    private String bornDate;


    private int hours;


    private String state;


    private String city;


    private String address;


    private String description;


    private int volunteerMade;

    private String photo;


    private RolUser mail;

    private String password;


    private String volInterest;

    private List<Event> myEvents;


    public Volunteer() {
    }


    public Volunteer(Integer vol_id, String name, String lastname, String gender, String bornDate, int hours, String state, String city, String address, String description, int volunteerMade, String photo, RolUser mail, String password, String volInterest, List<Event> myEvents) {
        this.vol_id = vol_id;
        this.name = name;
        this.lastname = lastname;
        this.gender = gender;
        this.bornDate = bornDate;
        this.hours = hours;
        this.state = state;
        this.city = city;
        this.address = address;
        this.description = description;
        this.volunteerMade = volunteerMade;
        this.photo = photo;
        this.mail = mail;
        this.password = password;
        this.volInterest = volInterest;
        this.myEvents = myEvents;
    }

    public Integer getVol_id() {
        return vol_id;
    }

    public void setVol_id(Integer vol_id) {
        this.vol_id = vol_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBornDate() {
        return bornDate;
    }

    public void setBornDate(String bornDate) {
        this.bornDate = bornDate;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
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

    public int getVolunteerMade() {
        return volunteerMade;
    }

    public void setVolunteerMade(int volunteerMade) {
        this.volunteerMade = volunteerMade;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public RolUser getMail() {
        return mail;
    }

    public void setMail(RolUser mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVolInterest() {
        return volInterest;
    }

    public void setVolInterest(String volInterest) {
        this.volInterest = volInterest;
    }


    public List<Event> getMyEvents() { return this.myEvents; }

    public void setMyEvents(List<Event> myEvents) { this.myEvents = myEvents; }



    @Override
    public String toString(){
        return "[Volunteer -> name: "+name+", id: "+Integer.toString(vol_id)+", lastname: "+lastname+", gender: "+gender+", hours: "+Integer.toString(hours)+", state: "+state+", city: "+city+", address: "+address+", description: "+description+", volunteersmade: "+Integer.toString(volunteerMade)+", photo: "+photo+", volPassword: "+password+", interest: "+volInterest+"]";
    }
}
