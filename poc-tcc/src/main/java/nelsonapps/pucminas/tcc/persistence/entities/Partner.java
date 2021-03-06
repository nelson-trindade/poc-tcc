package nelsonapps.pucminas.tcc.persistence.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Entity(name="partner")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="partner_type")
public class Partner {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(updatable=false,nullable=false)
	private Long Id;
	
	@NotBlank
	@Size(max=50)
	@Column(unique=true)
	private String name;
	
	@NotBlank
	@Size(max=500)
	private String address;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
