package nelsonapps.pucminas.tcc.persistence.entities;

import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import nelsonapps.pucminas.tcc.constants.Constants;

@Entity
public class Client extends Partner {

	@NotBlank
	@Pattern(regexp=Constants.cpfRegex)
	private String cpf;
	
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
}
