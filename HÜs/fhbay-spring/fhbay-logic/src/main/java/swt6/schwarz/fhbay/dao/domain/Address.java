package swt6.schwarz.fhbay.dao.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Address implements Serializable {
    @Id @GeneratedValue
    private Long id;
    private String   zipCode;
    private String   city;
    private String   street;


    public Address() {
    }

    public Address(String zipCode, String city, String street) {
        this.zipCode = zipCode;
        this.city = city;
        this.street = street;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
