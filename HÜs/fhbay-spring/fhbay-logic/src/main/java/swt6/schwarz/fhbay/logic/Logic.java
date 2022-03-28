package swt6.schwarz.fhbay.logic;


import swt6.schwarz.fhbay.dao.domain.Article;
import swt6.schwarz.fhbay.dao.domain.Bid;
import swt6.schwarz.fhbay.dao.domain.Customer;

import java.util.List;
import java.util.Optional;

public interface Logic {
    public Customer updateOrCreateCustomer(Customer customer);
    public Optional<Customer> findCustomerById(Long id);
    public List<Customer> getAllCustomers();

    public Article updateOrCreateArticle(Article article);
    public Optional<Article> findArticleById(Long id);
    public List<Article> getAllArticles();
    public Article setBuyer(Long articleId, Customer buyer);
    public Article setSeller (Long articleId, Customer seller);
    public List<Article> findByTerm(String searchTerm);

    public Bid updateOrCreateBid(Bid bid);
    public Bid addBid(Long customerId, Long articleId, double price);
    public List<Bid> getBidsForArticle(Long articleId);

}
