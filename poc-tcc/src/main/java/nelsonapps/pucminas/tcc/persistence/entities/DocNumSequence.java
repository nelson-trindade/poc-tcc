package nelsonapps.pucminas.tcc.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

@Entity
public class DocNumSequence {

	@TableGenerator(
			name="docNumSeq",
			table="ID_GEN",
			pkColumnName="GEN_KEY",
			valueColumnName="GEN_VALUE",
			pkColumnValue="DOCNUM_ID",
			allocationSize=10
			)	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="docNumSeq")
	private Long Id;
	
	public Long getId(){
		return Id;
	}
	
	public void setId(Long Id){
		this.Id = Id;
	}
}
