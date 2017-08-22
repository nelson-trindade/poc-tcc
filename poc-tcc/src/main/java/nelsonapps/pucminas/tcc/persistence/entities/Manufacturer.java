package nelsonapps.pucminas.tcc.persistence.entities;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import nelsonapps.pucminas.tcc.constants.Constants;

@Entity(name="manufacturer")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="PartnerType")
public class Manufacturer extends Partner {

	@NotBlank
	@Pattern(regexp=Constants.cnpjRegex)
	private String cnpj;
	
	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
}
