package com.mohan.contactsmap.model;

/**
 * Created by mohan on 20/12/16.
 */

public class Contacts {

    private long id;
    private String name;
    private String imageURI;
    private String contactNumber1;
    private String contactNumber2;
    private String email;
    private double lattitude;
    private double longitude;

    public double getLongitude() {
        return longitude;
    }

    public String getContactNumber1() {
        return contactNumber1;
    }

    public void setContactNumber1(String contactNumber1) {
        this.contactNumber1 = contactNumber1;
    }

    public String getContactNumber2() {
        return contactNumber2;
    }

    public void setContactNumber2(String contactNumber2) {
        this.contactNumber2 = contactNumber2;
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
