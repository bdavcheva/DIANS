package mk.ukim.finki.dians_dopolnitelno.repository;

import mk.ukim.finki.dians_dopolnitelno.model.FinancialRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long> {
    public List<FinancialRecord> findBySymbol(String symbol);
}
