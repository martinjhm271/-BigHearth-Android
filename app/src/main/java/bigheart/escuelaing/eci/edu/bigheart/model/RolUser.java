/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigheart.escuelaing.eci.edu.bigheart.model;



/**
 *
 * @author Carlos Ramirez
 */

public class RolUser implements java.io.Serializable{
    

    private String mail;
    private Roles rol_id;

    public RolUser() {
    }

    public RolUser(String mail, Roles rol_id) {
        this.mail = mail;
        this.rol_id = rol_id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Roles getRol_id() {
        return rol_id;
    }

    public void setRol_id(Roles rol_id) {
        this.rol_id = rol_id;
    }

    @Override
    public String toString(){
        return "[RolUser -> mail: "+mail+", rol: "+rol_id.toString()+"]";
    }
    
}
