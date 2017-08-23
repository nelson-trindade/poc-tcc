package nelsonapps.pucminas.tcc.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nelsonapps.pucminas.tcc.persistence.entities.DocNumSequence;

@Repository("docNumSequenceRepository")
public interface DocNumSequenceRepository extends JpaRepository<DocNumSequence, Long> {

}
