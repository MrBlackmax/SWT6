import org.junit.jupiter.api.*;
import swt6.schwarz.dao.orm.domain.*;
import swt6.schwarz.dao.orm.repositories.impl.ArticleRepository;
import swt6.schwarz.dao.orm.repositories.impl.BidRepository;
import swt6.schwarz.dao.orm.repositories.impl.CustomerRepository;

import java.time.LocalDateTime;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DaoTest {

    CustomerRepository customerRepository;
    ArticleRepository articleRepository;
    BidRepository bidRepository;
    Customer testCustomer;
    Article testArticle;
    Bid testBid;

    public void seedDB() {
        customerRepository = new CustomerRepository();
        customerRepository.open();
        testCustomer = new Customer("Test", "Person", "test@test.at");
        testCustomer.setBillingAddress(new Address("4082", "Aschach", "Hauptstraße 1"));
        testCustomer.addPaymentMethod(new CreditCard("Test", "2323232323232232", 7, 24,456));
        testCustomer = customerRepository.add(testCustomer);
        customerRepository.close();

        articleRepository = new ArticleRepository();
        articleRepository.open();
        testArticle = new Article("Minecraft", "A classic sandbox game", 0,20, LocalDateTime.now(), LocalDateTime.of(2022,12,24,23,59), testCustomer);
        testArticle = articleRepository.add(testArticle);
        articleRepository.close();

        bidRepository = new BidRepository();
        bidRepository.open();
        testBid = bidRepository.add(new Bid(testArticle, testCustomer, 50, LocalDateTime.now()));
        bidRepository.close();
    }

    @BeforeAll
    public void setUp() {
      seedDB();
    }

    @Test
    public void AddedCustomerGetsId() {
        customerRepository.open();
        var secondCustomer = new Customer("Sabrina", "Schwarz", "sabsi@gmx.at");
        Assertions.assertNull(secondCustomer.getId());
        secondCustomer = customerRepository.add(secondCustomer);
        Assertions.assertNotNull(secondCustomer.getId());
        customerRepository.close();
    }

    @Test
    public void CustomerWithCorrectIdIsFound() {
        customerRepository.open();
        var foundCustomer = customerRepository.findById(testCustomer.getId());
        Assertions.assertNotNull(foundCustomer);
        customerRepository.close();
    }

    @Test
    public void CustomerByIdMatchesName() {
        customerRepository.open();
        var customer = customerRepository.findById(testCustomer.getId());
        Assertions.assertEquals("Test", customer.getFirstName());
        customerRepository.close();
    }

    @Test
    public void ArticleWithCorrectTermIsFound() {
        articleRepository.open();
        var foundArticles = articleRepository.searchByTerm("sandbox");
        Assertions.assertEquals(1, foundArticles.size());
        articleRepository.close();
    }

    @Test
    public void ArticleCurrentPriceIsBidAmount() {
        articleRepository.open();
        var price = articleRepository.getCurrentPrice(testArticle);
        Assertions.assertEquals(50, price);
        articleRepository.close();
    }

    @AfterAll
    public void clean() {
        bidRepository.open();
        bidRepository.remove(testBid);
        bidRepository.close();

        articleRepository.open();
        articleRepository.remove(testArticle);
        articleRepository.close();

        customerRepository.open();
        customerRepository.remove(testCustomer);
        customerRepository.close();
    }
}
