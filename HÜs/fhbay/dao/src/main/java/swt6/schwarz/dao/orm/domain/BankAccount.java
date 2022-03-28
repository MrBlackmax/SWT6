package swt6.schwarz.dao.orm.domain;

import javax.persistence.Entity;

@Entity
public class BankAccount extends Payment{
    private static final long serialVersionUID = 1L;

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
