package swt6.schwarz.dao.orm.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Bid {
    @Id @GeneratedValue
    private Long id;
    private double amount;
    private LocalDateTime date;

    @ManyToOne (cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Customer bidder;

    @ManyToOne (cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Article article;

    public Bid() {
    }

    public Bid(Article article,Customer bidder, double amount, LocalDateTime date) {
        this.article = article;
        this.bidder = bidder;
        this.amount = amount;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Customer getBidder() {
        return bidder;
    }

    public void setBidder(Customer bidder) {
        this.bidder = bidder;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    @Override
    public String toString() {
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return "Bid: " + amount + " € by " + bidder.getEmail() + " (" + date.format(formatter) + ")";
    }
}
