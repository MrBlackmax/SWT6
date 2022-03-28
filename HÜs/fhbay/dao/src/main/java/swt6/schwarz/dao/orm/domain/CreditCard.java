package swt6.schwarz.dao.orm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
public class CreditCard extends Payment{
    private static final long serialVersionUID = 1L;

    private String cardNumber;
    private int expirationMonth;
    private int expirationYear;
    private int safetyCode;

    public CreditCard() {
    }

    public CreditCard(String holderName, String cardNumber, int expirationMonth, int expirationYear, int safetyCode) {
        super(holderName);
        this.cardNumber = cardNumber;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
        this.safetyCode = safetyCode;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(int expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    public int getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(int expirationYear) {
        this.expirationYear = expirationYear;
    }

    public int getSafetyCode() {
        return safetyCode;
    }

    public void setSafetyCode(int safetyCode) {
        this.safetyCode = safetyCode;
    }

    @Override
    public String toString() {
        return "Card: " + cardNumber.toString();
    }
}
