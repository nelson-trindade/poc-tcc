package nelsonapps.pucminas.tcc.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name="docNumSeq",sequenceName="docNumSeq",allocationSize=10)
public class DocNumSequence {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="docNumSeq")
	private Long Id;
	
	public Long getId(){
		return Id;
	}
}
