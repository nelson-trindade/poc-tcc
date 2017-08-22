package nelsonapps.pucminas.tcc.persistence.entities;

import javax.persistence.Cacheable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name="supplier")
@DiscriminatorValue(value="supplier")
@Cacheable
public class Supplier extends Manufacturer {

}
