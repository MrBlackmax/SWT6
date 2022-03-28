package swt6.schwarz.fhbay.shell;

import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import swt6.schwarz.fhbay.dao.domain.Article;
import swt6.schwarz.fhbay.logic.Logic;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@ShellComponent
public class ShellCommands {

    @Autowired
    private LogicConnection logicConnection;

    private String convertListToString(List items) {
        var builder = new StringBuilder();
        items.forEach(i -> {
            builder.append(i.toString());
            builder.append("\n");
        });
        return builder.toString();
    }

    @ShellMethod("Get All Customers of FhBay")
    public String getAllCustomers() {
        var customers = logicConnection.getLogic().getAllCustomers();
        return convertListToString(customers);
    }

    @ShellMethod("Find a customer by his id")
    public String findCustomerById(@ShellOption int id) {
        var foundCustomer = logicConnection.getLogic().findCustomerById(Long.valueOf(id));
        if (foundCustomer.isPresent()) return foundCustomer.get().toString();
        return "Customer with id " + id + " not found";
    }

    //String title, String description, double initialPrice, LocalDateTime beginDate, LocalDateTime endDate

    @ShellMethod("Get all articles of FhBay")
    public String getAllArticles() {
        var articles = logicConnection.getLogic().getAllArticles();
        return convertListToString(articles);
    }

    @ShellMethod("Find an article by his id")
    public String findArticleById(@ShellOption int id) {
        var foundArticle = logicConnection.getLogic().findArticleById(Long.valueOf(id));
        if (foundArticle.isPresent()) return foundArticle.get().toString();
        return "Article with id " + id + " not found";
    }

    @ShellMethod("Add a new article to the system")
    public String addArticle(@ShellOption String title, @ShellOption String desc, @ShellOption double price, @ShellOption String beginDate, @ShellOption String endDate) {
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        var beginDateParsed = LocalDateTime.parse(beginDate, formatter);
        var endDateParsed = LocalDateTime.parse(endDate, formatter);
        var article = new Article(title, desc, price, beginDateParsed, endDateParsed);
        var newArticle = logicConnection.getLogic().updateOrCreateArticle(article);
        return newArticle.toString();
    }

    @ShellMethod("Search article by term")
    public String search(@ShellOption String searchTerm) {
        var foundArticles = logicConnection.getLogic().findByTerm(searchTerm);
        if(foundArticles.isEmpty()) return  "No Articles found  with term '" + searchTerm + "'";
        return convertListToString(foundArticles);
    }

    @ShellMethod("Add a new bid for an article")
    public String addBid(@ShellOption int articleId, @ShellOption int customerId, @ShellOption double price) {
        return logicConnection.getLogic().addBid(Long.valueOf(customerId), Long.valueOf(articleId),price).toString();
    }

    @ShellMethod("Get all bids for an article")
    public String getAllBidsForArticle(@ShellOption int articleId) {
        var bids = logicConnection.getLogic().getBidsForArticle(Long.valueOf(articleId));
        return convertListToString(bids);
    }
}
