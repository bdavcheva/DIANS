package mk.ukim.finki.dians_dopolnitelno.model;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import java.time.LocalDateTime;

@Table("financial_records")
public class FinancialRecordCassandra {

    @PrimaryKey
    private String id;
    private String symbol;
    private double price;
    private LocalDateTime timestamp;

    public FinancialRecordCassandra() {}

    public FinancialRecordCassandra(String id, String symbol, double price, LocalDateTime timestamp) {
        this.id = id;
        this.symbol = symbol;
        this.price = price;
        this.timestamp = timestamp;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
