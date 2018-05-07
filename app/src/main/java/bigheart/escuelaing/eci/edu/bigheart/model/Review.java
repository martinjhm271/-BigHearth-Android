/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigheart.escuelaing.eci.edu.bigheart.model;

import java.io.Serializable;




public class Review implements Serializable{
    private int id;
    private String comment;
    private int score;
    private Event event;

    public Review() {
    }

    public Review(int id, String comment, int score, Event event) {
        this.id = id;
        this.comment = comment;
        this.score = score;
        this.event = event;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Event getEvent() { return event; }

    public void setEvent(Event event) { this.event = event; }
    
}
