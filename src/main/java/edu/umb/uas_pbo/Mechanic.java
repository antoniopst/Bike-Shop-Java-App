package edu.umb.uas_pbo;

import javafx.beans.property.*;

public class Mechanic {
    private IntegerProperty id;
    private StringProperty name;
    private StringProperty specialization;
    private StringProperty contact;

    // Constructor
    public Mechanic(int id, String name, String specialization, String contact) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.specialization = new SimpleStringProperty(specialization);
        this.contact = new SimpleStringProperty(contact);
    }

    // Default Constructor
    public Mechanic() {
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.specialization = new SimpleStringProperty();
        this.contact = new SimpleStringProperty();
    }

    // Getters and Setters for id
    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    // Getters and Setters for name
    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    // Getters and Setters for specialization
    public String getSpecialization() {
        return specialization.get();
    }

    public void setSpecialization(String specialization) {
        this.specialization.set(specialization);
    }

    public StringProperty specializationProperty() {
        return specialization;
    }

    // Getters and Setters for contact
    public String getContact() {
        return contact.get();
    }

    public void setContact(String contact) {
        this.contact.set(contact);
    }

    public StringProperty contactProperty() {
        return contact;
    }

    @Override
    public String toString() {
        return "Mechanic{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", specialization='" + getSpecialization() + '\'' +
                ", contact='" + getContact() + '\'' +
                '}';
    }
}
