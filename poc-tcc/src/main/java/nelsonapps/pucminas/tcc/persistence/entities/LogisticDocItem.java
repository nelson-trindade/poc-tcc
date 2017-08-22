package nelsonapps.pucminas.tcc.persistence.entities;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Entity
public class LogisticDocItem {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(updatable=false,nullable=false)
	private Long Id;
	
	@NotNull
	@DecimalMin("0.00")
	@Column(precision=3)
	private BigDecimal quantity;
	
	@NotNull
	private Date deliveryDate;
	
	@NotNull
	@DecimalMin("0.00")
	@Column(precision=2)
	private BigDecimal price;
	
	@NotNull
	private Date expiringDate;
	
	private String returnReason;
	
	
	
	@ManyToOne
	private LogisticDoc docHeader;
	
}
