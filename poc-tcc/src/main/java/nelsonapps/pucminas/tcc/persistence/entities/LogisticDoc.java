package nelsonapps.pucminas.tcc.persistence.entities;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class LogisticDoc {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(updatable=false,nullable=false)
	private Long Id;
	
	@NotNull
	private Date createdDate;
	
	private Date lastUpdateDate;
	
	@NotBlank
	private String docType;
	
	@OneToOne(optional=false)
	private DocNumSequence docNum;

	@OneToMany(mappedBy="docHeader")
	private Collection<LogisticDocItem> items;
	
	@ManyToOne(optional=false)
	private Partner partner;
	
	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getDocType() {
		return docType;
	}
	
	public void setDocType(String docType) {
		this.docType = docType;
	}

	public Collection<LogisticDocItem> getItems() {
		return items;
	}

	public void setItems(Collection<LogisticDocItem> items) {
		this.items = items;
	}

	public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}

	public DocNumSequence getDocNum() {
		return docNum;
	}
	
	public void setDocNum(DocNumSequence docNum){
		this.docNum = docNum;
	}

}
