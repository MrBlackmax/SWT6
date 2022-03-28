package swt6.schwarz.dao.orm.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue
    private Long id;

    private String firstName;
    private String lastName;
    private String email;

    @OneToOne (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Address shippingAddress;
    @OneToOne (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Address billingAddress;

    @OneToMany (cascade = CascadeType.ALL, mappedBy = "holder", fetch = FetchType.EAGER)
    private Set<Payment> paymentMethods = new HashSet<>();

    public Customer() {
    }

    public Customer(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public Set<Payment> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(Set<Payment> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public void addPaymentMethod(Payment method) {
        this.paymentMethods.add(method);
        method.setHolder(this);
    }

    public void removePaymentMethod(Payment method) {
        method.setHolder(null);
        this.paymentMethods.remove(method);
    }

    @Override
    public String toString() {
        return "#" + id + ": " + firstName + " " + lastName + "(" + email +")";
    }
}
