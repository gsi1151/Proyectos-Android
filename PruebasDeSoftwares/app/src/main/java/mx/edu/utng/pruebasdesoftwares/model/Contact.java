package mx.edu.utng.pruebasdesoftwares.model;

/**
 * Created by griselda on 02/03/2016.
 */
public class Contact {
    String name, email,uname,pass;


    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return  this.name;
    }
    public String setEmail(String email){
        this.email=email;

        return email;
    }
    public String getEmail(){
        return  this.email;
    }
    public void setUname(String uname){
        this.uname=uname;
    }
    public String getUname(){
        return  this.uname;
    }
    public void setPass(String pass){
        this.pass=pass;
    }
    public String getPass(){
        return  this.pass;
    }

}
