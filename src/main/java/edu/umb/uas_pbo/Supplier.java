package edu.umb.uas_pbo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Supplier {
    private final IntegerProperty id;
    private final StringProperty name;
    private final StringProperty contact;
    private final StringProperty address;

    public Supplier(int id, String name, String contact, String address) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.contact = new SimpleStringProperty(contact);
        this.address = new SimpleStringProperty(address);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty contactProperty() {
        return contact;
    }

    public StringProperty addressProperty() {
        return address;
    }
}