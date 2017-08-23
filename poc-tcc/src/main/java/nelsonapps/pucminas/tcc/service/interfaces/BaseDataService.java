package nelsonapps.pucminas.tcc.service.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class BaseDataService<E,R extends JpaRepository<E, Long>> implements IBaseDataService<E> {

	@Autowired
	protected R repository;
	
	public E save(E entity){
		return repository.save(entity);
	}
}
