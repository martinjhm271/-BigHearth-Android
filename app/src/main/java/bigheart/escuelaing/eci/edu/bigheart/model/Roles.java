/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigheart.escuelaing.eci.edu.bigheart.model;




public class Roles implements java.io.Serializable{
    


    private int rolid;
    

    private String name;

    public Roles() {
    }

    public Roles(int rolid, String name) {
        this.rolid = rolid;
        this.name = name;
    }

    public int getRolid() {
        return rolid;
    }

    public void setRolid(int rolid) {
        this.rolid = rolid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString(){
        return "RolId[id: "+Integer.toString(rolid)+" ,name: "+name+"]";
    } 
    
}
