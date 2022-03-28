package swt6.schwarz.dao.orm.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Payment.class)
public abstract class Payment_ {

	public static volatile SingularAttribute<Payment, String> holderName;
	public static volatile SingularAttribute<Payment, Customer> holder;
	public static volatile SingularAttribute<Payment, Long> id;

	public static final String HOLDER_NAME = "holderName";
	public static final String HOLDER = "holder";
	public static final String ID = "id";

}

