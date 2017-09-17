package nelsonapps.pucminas.tcc.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nelsonapps.pucminas.tcc.persistence.entities.LogisticDocItem;

@Repository("logisticDocItemRepository")
public interface LogisticDocItemRepository extends JpaRepository<LogisticDocItem, Long> {

}
