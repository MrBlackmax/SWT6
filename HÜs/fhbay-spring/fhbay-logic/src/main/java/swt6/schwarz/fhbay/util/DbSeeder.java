package swt6.schwarz.fhbay.util;

import swt6.schwarz.fhbay.dao.domain.*;
import swt6.schwarz.fhbay.logic.Logic;

import java.time.LocalDateTime;

public class DbSeeder {
    private Logic fhBayLogic;

    private Customer maxschwarz;
    private Customer billgates;
    private Article psFive;
    private Bid bid;

    public DbSeeder(Logic fhBayLogic) {
        this.fhBayLogic = fhBayLogic;
    }

    public Logic getFhBayLogic() {
        return fhBayLogic;
    }

    public Customer getMaxschwarz() {
        return maxschwarz;
    }

    public Customer getBillgates() {
        return billgates;
    }

    public Article getPsFive() {
        return psFive;
    }

    public Bid getBid() {
        return bid;
    }

    public void seedDB() {
        addCustomers();
        addArticles();
        addBids();
    }

    public void addCustomers() {
        maxschwarz = new Customer("Max", "Schwarz","maxbox@gmx.at");
        billgates = new Customer("Bill", "Gates", "billgates@gmx.com");
        maxschwarz.setBillingAddress(new Address("4082", "Aschach", "Kirchenplatz 4/3"));
        maxschwarz.setShippingAddress(new Address("4082", "Aschach", "Kirchenplatz 4"));
        maxschwarz.addPaymentMethod(new BankAccount("Maximilian Schwarz", "AT04 2033 2023 0000 7966 5432"));

        maxschwarz = fhBayLogic.updateOrCreateCustomer(maxschwarz);
        billgates = fhBayLogic.updateOrCreateCustomer(billgates);
    }

    public void addArticles() {
        psFive = new Article("PS5", "A fancy new game console", 399.99, LocalDateTime.now().plusSeconds(30), LocalDateTime.now().plusMinutes(1), billgates);
        fhBayLogic.updateOrCreateArticle(psFive);
    }

    public void addBids() {
        bid = new Bid(psFive, maxschwarz, 402, LocalDateTime.now());
        fhBayLogic.updateOrCreateBid(bid);
    }
}
