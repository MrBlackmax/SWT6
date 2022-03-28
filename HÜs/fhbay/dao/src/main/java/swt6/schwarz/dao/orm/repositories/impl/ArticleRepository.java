package swt6.schwarz.dao.orm.repositories.impl;

import swt6.schwarz.dao.orm.domain.Article;
import swt6.schwarz.dao.orm.domain.Bid;
import swt6.schwarz.dao.orm.domain.Bid_;
import swt6.schwarz.dao.orm.repositories.BaseRepository;
import swt6.schwarz.dao.orm.utils.DaoUtils;

import java.util.List;

public class ArticleRepository extends BaseRepository<Article> {

    @Override
    public Article update(Article entity) {
        return getEntityHandler().updateEntity(entity.getId(), entity, Article.class);
    }

    public List<Article> searchByTerm(String searchTerm) {
        try {
            var searchParam = "%" + searchTerm + "%";
            var query = getEm().createQuery("select a from Article a where a.title LIKE :tq OR a.description LIKE :dq", Article.class);
            query.setParameter("tq", searchParam);
            query.setParameter("dq", searchParam);
            return query.getResultList();
        } catch (Exception ex) {
            DaoUtils.rollback();
            throw ex;
        }
    }

    //gets price highest bid amount or initial price if no bids
    public double getCurrentPrice(Article article) {
        try {
            var cb = getEm().getCriteriaBuilder();
            var query = cb.createQuery(Bid.class);
            var root = query.from(Bid.class);
            var param = cb.parameter(Article.class);
            query.where(cb.equal(root.get(Bid_.article), param)).select(root);
            var entryQuery = getEm().createQuery(query);
            entryQuery.setParameter(param, article);
            var foundBids= entryQuery.getResultList();
            if (foundBids.size()  == 0) {
                return article.getInitialPrice();
            } else {
                return foundBids.stream().mapToDouble(b -> b.getAmount()).max().orElse(article.getInitialPrice());
            }
        } catch (Exception ex) {
            DaoUtils.rollback();
            throw ex;
        }
    }
}
