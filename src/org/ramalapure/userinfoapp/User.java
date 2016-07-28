/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ramalapure.userinfoapp;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Ram
 */
public class User {
    
        private final SimpleStringProperty ID;
        private final SimpleStringProperty firstName;
        private final SimpleStringProperty lastName;
        private final SimpleStringProperty email;
        private final SimpleStringProperty username;
        private final SimpleStringProperty password;
        private final SimpleStringProperty DOB;
        private final SimpleStringProperty Gender;
        private final SimpleStringProperty MobileNo;
        private final SimpleStringProperty Hobbies;
    
     public User(String id, String fName, String lName, String mail, String un, String pw, String dob, String gender, String mobileno, String hobbies) {
            this.ID = new SimpleStringProperty(id);
            this.firstName = new SimpleStringProperty(fName);
            this.lastName = new SimpleStringProperty(lName);
            this.email = new SimpleStringProperty(mail);
            this.username = new SimpleStringProperty(un);
            this.password = new SimpleStringProperty(pw);
            this.DOB = new SimpleStringProperty(dob);
            this.Gender = new SimpleStringProperty(gender);
            this.MobileNo = new SimpleStringProperty(mobileno);
            this.Hobbies = new SimpleStringProperty(hobbies);
        }
 
    public String getID(){
        return ID.get();
    }
    public String getFirstName(){
        return firstName.get();
    }
    public String getLastName(){
        return lastName.get();
    }
    public String getEmail(){
        return email.get();
    }
    public String getUsername(){
        return username.get();
    }
    public String getPassword(){
        return password.get();
    }
    public String getDOB(){
        return DOB.get();
    }
    public String getGender(){
        return Gender.get();
    }
    public String getMobileNo(){
        return MobileNo.get();
    }
    public String getHobbies(){
        return Hobbies.get();
    }
    public void setID(String id){
        ID.set(id);
    }
    public void setFirstName(String fName){
        firstName.set(fName);
    }
    public void setLastName(String lName){
        lastName.set(lName);
    }
    public void setEmail(String mail){
        email.set(mail);
    }
     public void setUsername(String un){
        username.set(un);
    }
    public void setPassword(String pw){
        password.set(pw);
    }
    public void setDOB(String dob){
        DOB.set(dob);
    }
    public void setGender(String gender){
        Gender.set(gender);
    }
     public void setMobileNo(String mobileno){
        MobileNo.set(mobileno);
    }
      public void setHobbies(String hobbies){
        Hobbies.set(hobbies);
    }
}
