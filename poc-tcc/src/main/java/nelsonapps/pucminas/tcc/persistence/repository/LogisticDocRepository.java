package nelsonapps.pucminas.tcc.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import nelsonapps.pucminas.tcc.persistence.entities.LogisticDoc;

@Repository("logisticDocRepository")
public interface LogisticDocRepository extends JpaRepository<LogisticDoc, Long>,
                                               QueryDslPredicateExecutor<LogisticDoc> {

}
