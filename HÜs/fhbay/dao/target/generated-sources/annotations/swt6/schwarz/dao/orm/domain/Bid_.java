package swt6.schwarz.dao.orm.domain;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Bid.class)
public abstract class Bid_ {

	public static volatile SingularAttribute<Bid, LocalDateTime> date;
	public static volatile SingularAttribute<Bid, Double> amount;
	public static volatile SingularAttribute<Bid, Customer> bidder;
	public static volatile SingularAttribute<Bid, Long> id;
	public static volatile SingularAttribute<Bid, Article> article;

	public static final String DATE = "date";
	public static final String AMOUNT = "amount";
	public static final String BIDDER = "bidder";
	public static final String ID = "id";
	public static final String ARTICLE = "article";

}

