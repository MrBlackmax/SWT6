package swt6.schwarz.fhbay.shell;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import swt6.schwarz.fhbay.dao.domain.Article;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class AuctionChecker {

    @Scheduled(fixedRate = 2000)
    private void checkForAuctions() {
        var logic = LogicConnection.getLogic();
        var articles = logic.getAllArticles();
        for (Article article : articles) {
            if (article.getEndDate().isBefore(LocalDateTime.now()) && article.getState() == 1) {
                article.setState(2);
                System.out.println("Auction closed for article " + article.getId());
                var bidsForArticle = logic.getBidsForArticle(article.getId());
                if (bidsForArticle.isEmpty()) {
                    System.out.println("No bid for this article");
                    article.setFinalPrice(article.getInitialPrice());
                } else {
                    var heighestBidAmount = bidsForArticle.stream().mapToDouble(b -> b.getAmount()).max();
                    var heighestBid = bidsForArticle.stream().filter(bid -> bid.getAmount() == heighestBidAmount.getAsDouble()).findFirst().get();
                    article.setBuyer(heighestBid.getBidder());
                    article.setFinalPrice(heighestBid.getAmount());
                    System.out.println("Highest Bid:");
                    System.out.println(heighestBid.toString());
                }
            } else if (article.getBeginDate().isBefore(LocalDateTime.now()) && article.getState() == 0) {
                System.out.println("Auction opened for article " + article.getId());
                article.setState(1);
            }
            article = logic.updateOrCreateArticle(article);
        }
    }
}
