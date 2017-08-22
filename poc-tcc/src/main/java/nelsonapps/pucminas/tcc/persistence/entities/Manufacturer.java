package nelsonapps.pucminas.tcc.persistence.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import nelsonapps.pucminas.tcc.constants.Constants;

@Entity(name="manufacturer")
@DiscriminatorValue("manufacturer")
public class Manufacturer extends Partner {

	@NotBlank
	@Pattern(regexp=Constants.cnpjRegex)
	@Column(unique=true)
	private String cnpj;
	
	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
}
