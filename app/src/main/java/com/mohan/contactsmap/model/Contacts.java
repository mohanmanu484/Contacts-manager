package com.mohan.contactsmap.model;

import java.util.ArrayList;

/**
 * Created by mohan on 20/12/16.
 */

public class Contacts {

    private long id;
    private String name;
    private String imageURI;
    private ArrayList<String> contactNumber;
    private String email;
    private double lattitude;
    private double longitude;

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLattitude() {
        return lattitude;
    }

    public void setLattitude(double lattitude) {
        this.lattitude = lattitude;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Contacts(long id) {
        this.id = id;
        this.contactNumber=new ArrayList<>();
    }

    public ArrayList<String> getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber.add(contactNumber);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURI() {
        return imageURI;
    }

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }

}
