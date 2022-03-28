package swt6.schwarz.dao.orm.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CreditCard.class)
public abstract class CreditCard_ extends swt6.schwarz.dao.orm.domain.Payment_ {

	public static volatile SingularAttribute<CreditCard, Integer> expirationYear;
	public static volatile SingularAttribute<CreditCard, Integer> expirationMonth;
	public static volatile SingularAttribute<CreditCard, Integer> safetyCode;
	public static volatile SingularAttribute<CreditCard, String> cardNumber;

	public static final String EXPIRATION_YEAR = "expirationYear";
	public static final String EXPIRATION_MONTH = "expirationMonth";
	public static final String SAFETY_CODE = "safetyCode";
	public static final String CARD_NUMBER = "cardNumber";

}

