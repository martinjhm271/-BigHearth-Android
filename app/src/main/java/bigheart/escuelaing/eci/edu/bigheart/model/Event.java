package bigheart.escuelaing.eci.edu.bigheart.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Event  implements Serializable{



    private int maxVolunteers;
    private int numberVolunteers;
    private String eventType;
    private String description;
    private Date eventDate;
    private String image;
    private List<Volunteer> volunteers=new ArrayList<>();
    private Organization organization;
    private List<Review> reviews;
    private List<Requirement> requirements;
    public Double latitude;
    public Double longitude;
    private String name;
    private int id;

    public Event(){

    }


    public Event(int id, int maxVolunteers, String eventType, String description, Date eventDate,String image, List<Volunteer> volunteers, Organization organization, List<Review> reviews, List<Requirement> requirements,Double latitude, Double longitude,String name, int numberVolunteers){
        this.id = id;
        this.maxVolunteers = maxVolunteers;
        this.eventType = eventType;
        this.description = description;
        this.eventDate = eventDate;
        this.image = image;
        this.volunteers = volunteers;
        this.organization = organization;
        this.reviews = reviews;
        this.requirements = requirements;
        this.latitude=latitude;
        this.longitude=longitude;
        this.name=name;
        this.numberVolunteers = numberVolunteers;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }


    public String getImage() {
        return image;
    }

    public void  setImage(String image) { this.image = image;}

    public List<Volunteer> getVolunteers() {
        return volunteers;
    }

    public void setVolunteers(List<Volunteer> volunteers) {
        this.volunteers = volunteers;
    }


    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }


    public int getMaxVolunteers() {
        return maxVolunteers;
    }

    public void setMaxVolunteers(int maxVolunteers) {
        this.maxVolunteers = maxVolunteers;
    }


    public Organization getOrganization(){
        return this.organization;
    }

    public void setOrganization(Organization organization){
        this.organization=organization;
    }


    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }


    public List<Review> getReviews() {return reviews; }

    public void setReviews(List<Review> reviews) { this.reviews = reviews; }


    public List<Requirement> getRequirements() { return requirements; }

    public void setRequirements(List<Requirement> requirements) { this.requirements = requirements; }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberVolunteers() {
        return numberVolunteers;
    }

    public void setNumberVolunteers(int numberVolunteers) {
        this.numberVolunteers = numberVolunteers;
    }

    public String getDateFormat(){
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        return dt.format(this.eventDate);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "[Event -> id:"+this.id+" name:"+this.name+" maxVolunteers:"+this.maxVolunteers+" numberVolunteers:"+this.numberVolunteers+" type:"+this.eventType+" description:"+this.description+" date:"+this.eventDate+" image:"+this.image+" volunteers:"+this.volunteers+" reviews:"+this.reviews+" requierements:"+this.requirements+" latitude:"+this.latitude+" longitude:"+this.longitude+"]";
    }

}
