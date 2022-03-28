package swt6.schwarz.fhbay.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import swt6.schwarz.fhbay.dao.domain.Article;
import swt6.schwarz.fhbay.dao.domain.Bid;

import java.util.List;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {

    public List<Bid> getAllByArticle(@Param("article") Article article);
}
