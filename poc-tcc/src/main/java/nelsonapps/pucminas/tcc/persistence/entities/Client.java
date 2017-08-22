package nelsonapps.pucminas.tcc.persistence.entities;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import nelsonapps.pucminas.tcc.constants.Constants;

@Entity(name="client")
@DiscriminatorValue("client")
@Cacheable
public class Client extends Partner {

	@NotBlank
	@Pattern(regexp=Constants.cpfRegex)
	@Column(unique=true)
	private String cpf;
	
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
}
