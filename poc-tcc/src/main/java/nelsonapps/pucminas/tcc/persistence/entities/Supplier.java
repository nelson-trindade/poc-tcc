package nelsonapps.pucminas.tcc.persistence.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name="supplier")
@DiscriminatorValue(value="Supplier")
public class Supplier extends Manufacturer {

}
