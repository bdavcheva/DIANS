package mk.ukim.finki.dians_dopolnitelno.repository;

import mk.ukim.finki.dians_dopolnitelno.model.FinancialRecordCassandra;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinancialRecordCassandraRepository extends CassandraRepository<FinancialRecordCassandra, String> {
}
