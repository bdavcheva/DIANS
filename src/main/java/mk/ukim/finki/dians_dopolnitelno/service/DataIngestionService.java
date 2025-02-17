package mk.ukim.finki.dians_dopolnitelno.service;

import mk.ukim.finki.dians_dopolnitelno.model.FinancialRecord;
import mk.ukim.finki.dians_dopolnitelno.repository.FinancialRecordRepository;
import mk.ukim.finki.dians_dopolnitelno.repository.FinancialRecordCassandraRepository;
import org.json.JSONObject;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class DataIngestionService {

    @Autowired
    private FinancialRecordRepository financialRecordRepository; // PostgreSQL

    @Autowired
    private FinancialRecordCassandraRepository financialRecordCassandraRepository; // Cassandra

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${yahoo.finance.api.key}")
    private String apiKey;

    private static final String YAHOO_FINANCE_URL = "https://yahoo-finance15.p.rapidapi.com/api/yahoo/qu/quote";
    private static final String TOPIC = "financial-data";

    public void fetchAndStoreStockData(String symbol) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-rapidapi-key", apiKey);
        headers.set("x-rapidapi-host", "yahoo-finance15.p.rapidapi.com");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        String url = UriComponentsBuilder.fromHttpUrl(YAHOO_FINANCE_URL)
                .queryParam("symbols", symbol)
                .toUriString();

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            String jsonResponse = response.getBody();
            FinancialRecord financialRecord = parseJsonToFinancialRecord(jsonResponse);

            financialRecordRepository.save(financialRecord); // PostgreSQL
            financialRecordCassandraRepository.save(new FinancialRecordCassandra(
                    UUID.randomUUID().toString(),
                    financialRecord.getSymbol(),
                    financialRecord.getPrice(),
                    financialRecord.getTimestamp()
            ));

            kafkaTemplate.send(TOPIC, jsonResponse);
        }
    }

    private FinancialRecord parseJsonToFinancialRecord(String jsonResponse) {
        JSONObject json = new JSONObject(jsonResponse);
        JSONObject quoteResponse = json.getJSONObject("quoteResponse");
        JSONArray resultArray = quoteResponse.getJSONArray("result");
        if (resultArray.length() == 0) return new FinancialRecord();

        JSONObject stockData = resultArray.getJSONObject(0);
        String symbol = stockData.getString("symbol");
        double price = stockData.getDouble("regularMarketPrice");
        LocalDate timestamp = LocalDate.now();

        return new FinancialRecord(symbol, price, timestamp);
    }

    @Scheduled(fixedRate = 300000)         //за update на податоци на 5 минути (300,000ms)
    public void scheduledDataFetch() {
        String[] symbols = {"AAPL", "TSLA", "AMZN", "GOOGL"};
        for (String symbol : symbols) {
            fetchAndStoreStockData(symbol);
        }
    }
}
