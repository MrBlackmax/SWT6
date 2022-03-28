package swt6.schwarz.fhbay.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import swt6.schwarz.fhbay.dao.domain.Article;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("select a from Article a where a.title LIKE %:term% OR a.description LIKE %:term%")
    public List<Article> findByTerm(@Param("term") String searchTerm);
}
