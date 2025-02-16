package mk.ukim.finki.dians_dopolnitelno.service;

import mk.ukim.finki.dians_dopolnitelno.model.FinancialRecord;
import mk.ukim.finki.dians_dopolnitelno.repository.FinancialRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FinancialRecordService {
    private final FinancialRecordRepository financialRecordRepository;

    public FinancialRecordService(FinancialRecordRepository financialRecordRepository) {
        this.financialRecordRepository = financialRecordRepository;
    }

    public List<FinancialRecord> getAllRecords() {
        return financialRecordRepository.findAll();
    }

    public Optional<FinancialRecord> getRecordById(Long id) {
        return financialRecordRepository.findById(id);
    }

    public FinancialRecord saveRecord(FinancialRecord record) {
        return financialRecordRepository.save(record);
    }

    public void deleteRecord(Long id) {
        financialRecordRepository.deleteById(id);
    }
}
