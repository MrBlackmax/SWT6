package swt6.schwarz.dao.orm.domain;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Payment implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue
    private Long id;
    private String holderName;
    @ManyToOne (fetch = FetchType.LAZY)
    private Customer holder;

    public Payment() {
    }

    public Payment(String holderName) {
        this.holderName = holderName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public Customer getHolder() {
        return holder;
    }

    public void setHolder(Customer holder) {
        this.holder = holder;
    }
}
