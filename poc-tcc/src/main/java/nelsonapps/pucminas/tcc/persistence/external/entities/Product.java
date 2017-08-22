package nelsonapps.pucminas.tcc.persistence.external.entities;

import java.util.UUID;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import nelsonapps.pucminas.tcc.constants.Constants;

@Entity
@Cacheable
public class Product {

	@Id
	@GeneratedValue
	@Column(updatable=false,nullable=false)
	private Long Id;
	
	private String productUUID = UUID.randomUUID().toString();
	
	@NotBlank
	@Size(max=500)
	private String description;
	
	@NotBlank
	@Size(max=50)
	private String shortName;
	
	@NotBlank
	@Size(max=4)
	private String measureUnit;
	
	@NotBlank
	@Pattern(regexp=Constants.cnpjRegex)
	private String manufacturerCnpj;
	
	@NotNull
	private boolean returnablePackage;
	
	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getProductUUID() {
		return productUUID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getMeasureUnit() {
		return measureUnit;
	}

	public void setMeasureUnit(String measureUnit) {
		this.measureUnit = measureUnit;
	}

	public boolean isReturnablePackage() {
		return returnablePackage;
	}

	public void setReturnablePackage(boolean returnablePackage) {
		this.returnablePackage = returnablePackage;
	}

	public String getManufacturerCnpj() {
		return manufacturerCnpj;
	}

	public void setManufacturerCnpj(String manufacturerCnpj) {
		this.manufacturerCnpj = manufacturerCnpj;
	}
	
}
