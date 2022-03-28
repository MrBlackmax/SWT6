package swt6.schwarz.clients;

import swt6.schwarz.dao.orm.domain.*;
import swt6.schwarz.dao.orm.repositories.impl.ArticleRepository;
import swt6.schwarz.dao.orm.repositories.impl.BidRepository;
import swt6.schwarz.dao.orm.repositories.impl.CustomerRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TestClient {

    public static void main(String[] args) {
        var customer = new Customer("Max", "Schwarz","maxbox@gmx.at");
        var testSeller = new Customer("Bill", "Gates", "billgates@gmx.com");
        var psFive = new Article("PS5", "A fancy new game console", 0, 399.99, LocalDateTime.now(), LocalDateTime.of(2022,12,31,23,59), testSeller);
        var bid = new Bid(psFive, customer, 402, LocalDateTime.now());
        bid.setBidder(customer);
        customer.setBillingAddress(new Address("4082", "Aschach", "Kirchenplatz 4/3"));
        customer.setShippingAddress(new Address("4082", "Aschach", "Kirchenplatz 4"));
        customer.addPaymentMethod(new BankAccount("Maximilian Schwarz", "AT04 2033 2023 0000 7966 5432"));

        System.out.println("-----------------------------Add customers-----------------------------");
        var customerRepo = new CustomerRepository();
        customerRepo.open();
        customer = customerRepo.add(customer);
        testSeller = customerRepo.add(testSeller);
        System.out.println(customer);
        customerRepo.close();

        System.out.println("-----------------------------Add Article-----------------------------");
        var articleRepo = new ArticleRepository();
        articleRepo.open();
        psFive = articleRepo.add(psFive);
        System.out.println(psFive);
        articleRepo.close();

        System.out.println("-----------------------------Add bid-----------------------------");
        var bidRepo = new BidRepository();
        bidRepo.open();
        bid = bidRepo.add(bid);
        //bidRepo.add(null); // test for rollback
        System.out.println(bid);
        bidRepo.close();

        System.out.println("-----------------------------Add bid by updating article-----------------------------");
        psFive.addBid(new Bid(psFive, customer, 500, LocalDateTime.now()));
        articleRepo.open();
        psFive = articleRepo.update(psFive);
        System.out.println(psFive.getBids().stream().toList().get(psFive.getBids().size()-1));
        articleRepo.close();

        System.out.println("-----------------------------Search by 'console'-----------------------------");
        articleRepo.open();
        var articles = articleRepo.searchByTerm("console");
        System.out.println("Found articles:");
        articles.forEach(System.out::println);
        var currentPrice = articleRepo.getCurrentPrice(psFive);
        System.out.println("Current Price for PS5:" + currentPrice);
        articleRepo.close();
    }
}
