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

import org.hibernate.validator.constraints.NotBlank;

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
	
	@NotBlank
	private String productUUID;
	
	@ManyToOne(optional=false)
	private LogisticDoc docHeader;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Date getExpiringDate() {
		return expiringDate;
	}

	public void setExpiringDate(Date expiringDate) {
		this.expiringDate = expiringDate;
	}

	public String getReturnReason() {
		return returnReason;
	}

	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}

	public String getProductUUID() {
		return productUUID;
	}

	public void setProductUUID(String productUUID) {
		this.productUUID = productUUID;
	}

	public LogisticDoc getDocHeader() {
		return docHeader;
	}

	public void setDocHeader(LogisticDoc docHeader) {
		this.docHeader = docHeader;
	}
	
}
