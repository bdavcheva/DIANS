package mk.ukim.finki.dians_dopolnitelno.controller;

import mk.ukim.finki.dians_dopolnitelno.model.FinancialRecord;
import mk.ukim.finki.dians_dopolnitelno.service.FinancialRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/financial-records")
public class FinancialRecordController {

    private final FinancialRecordService financialRecordService;

    public FinancialRecordController(FinancialRecordService financialRecordService) {
        this.financialRecordService = financialRecordService;
    }

    @GetMapping
    public List<FinancialRecord> getAllRecords() {
        return financialRecordService.getAllRecords();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FinancialRecord> getRecordById(@PathVariable Long id) {
        Optional<FinancialRecord> record = financialRecordService.getRecordById(id);
        return record.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public FinancialRecord createRecord(@RequestBody FinancialRecord record) {
        return financialRecordService.saveRecord(record);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecord(@PathVariable Long id) {
        financialRecordService.deleteRecord(id);
        return ResponseEntity.noContent().build();
    }
}
