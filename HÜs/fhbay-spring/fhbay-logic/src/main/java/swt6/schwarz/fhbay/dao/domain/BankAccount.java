package swt6.schwarz.fhbay.dao.domain;

import javax.persistence.Entity;

@Entity
public class BankAccount extends Payment{

    private String iban;

    public BankAccount() {
    }

    public BankAccount(String holderName, String iban) {
        super(holderName);
        this.iban = iban;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    @Override
    public String toString() {
        return "BankAccount: " + iban;
    }
}
