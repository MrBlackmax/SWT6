package swt6.schwarz.dao.orm.domain;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Article.class)
public abstract class Article_ {

	public static volatile SingularAttribute<Article, Customer> seller;
	public static volatile SingularAttribute<Article, LocalDateTime> beginDate;
	public static volatile SingularAttribute<Article, LocalDateTime> endDate;
	public static volatile SingularAttribute<Article, Double> initialPrice;
	public static volatile SetAttribute<Article, Bid> bids;
	public static volatile SingularAttribute<Article, String> description;
	public static volatile SingularAttribute<Article, Double> finalPrice;
	public static volatile SingularAttribute<Article, Long> id;
	public static volatile SingularAttribute<Article, Integer> state;
	public static volatile SingularAttribute<Article, String> title;
	public static volatile SingularAttribute<Article, Customer> buyer;

	public static final String SELLER = "seller";
	public static final String BEGIN_DATE = "beginDate";
	public static final String END_DATE = "endDate";
	public static final String INITIAL_PRICE = "initialPrice";
	public static final String BIDS = "bids";
	public static final String DESCRIPTION = "description";
	public static final String FINAL_PRICE = "finalPrice";
	public static final String ID = "id";
	public static final String STATE = "state";
	public static final String TITLE = "title";
	public static final String BUYER = "buyer";

}

