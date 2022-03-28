package swt6.schwarz.fhbay.dao.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Article implements Serializable {

    @Id @GeneratedValue
    private Long id;

    private String title;
    private String description;
    private int state;
    private double initialPrice;
    private double finalPrice;
    private LocalDateTime beginDate;
    private LocalDateTime endDate;
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Customer seller;
    @ManyToOne (cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Customer buyer;
    @OneToMany (cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "article")
    private Set<Bid> bids = new HashSet<>();

    public Article() {
    }

    public Article(String title, String description, double initialPrice, LocalDateTime beginDate, LocalDateTime endDate, Customer seller) {
        this.title = title;
        this.description = description;
        this.state = 0;
        this.initialPrice = initialPrice;
        this.finalPrice = 0.0;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.seller = seller;
    }

    public Article(String title, String description, double initialPrice, LocalDateTime beginDate, LocalDateTime endDate) {
        this.title = title;
        this.description = description;
        this.state = 0;
        this.initialPrice = initialPrice;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public double getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(double initialPrice) {
        this.initialPrice = initialPrice;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public LocalDateTime getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDateTime beginDate) {
        this.beginDate = beginDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Customer getSeller() {
        return seller;
    }

    public void setSeller(Customer seller) {
        this.seller = seller;
    }

    public Customer getBuyer() {
        return buyer;
    }

    public void setBuyer(Customer buyer) {
        this.buyer = buyer;
    }

    public Set<Bid> getBids() {
        return bids;
    }

    public void setBids(Set<Bid> bids) {
        this.bids = bids;
    }

    public void addBid(Bid bid) {
        this.bids.add(bid);
        bid.setArticle(this);
    }

    @Override
    public String toString() {
        return id + ": " + title + " (" + description + ")";
    }
}
