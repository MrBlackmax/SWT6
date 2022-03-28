package swt6.schwarz.dao.orm.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Customer.class)
public abstract class Customer_ {

	public static volatile SingularAttribute<Customer, String> firstName;
	public static volatile SingularAttribute<Customer, String> lastName;
	public static volatile SetAttribute<Customer, Payment> paymentMethods;
	public static volatile SingularAttribute<Customer, Address> shippingAddress;
	public static volatile SingularAttribute<Customer, Long> id;
	public static volatile SingularAttribute<Customer, Address> billingAddress;
	public static volatile SingularAttribute<Customer, String> email;

	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String PAYMENT_METHODS = "paymentMethods";
	public static final String SHIPPING_ADDRESS = "shippingAddress";
	public static final String ID = "id";
	public static final String BILLING_ADDRESS = "billingAddress";
	public static final String EMAIL = "email";

}

