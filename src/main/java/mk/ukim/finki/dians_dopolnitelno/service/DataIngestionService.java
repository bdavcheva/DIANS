package mk.ukim.finki.dians_dopolnitelno.service;

import mk.ukim.finki.dians_dopolnitelno.model.FinancialRecord;
import mk.ukim.finki.dians_dopolnitelno.repository.FinancialRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class DataIngestionService {

    @Autowired
    private FinancialRecordRepository financialRecordRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String ALPHA_VANTAGE_URL = "https://www.alphavantage.co/query";
    private static final String API_KEY = "api-key-here";
    private static final String TOPIC = "financial-data"; // Име на Kafka топикот

    public void fetchAndStoreStockData(String symbol) {
        String url = UriComponentsBuilder.fromHttpUrl(ALPHA_VANTAGE_URL)
                .queryParam("function", "TIME_SERIES_INTRADAY")
                .queryParam("symbol", symbol)
                .queryParam("interval", "5min")
                .queryParam("apikey", API_KEY)
                .toUriString();

        RestTemplate restTemplate = new RestTemplate();
        String jsonResponse = restTemplate.getForObject(url, String.class);

        FinancialRecord financialRecord = parseJsonToFinancialRecord(jsonResponse);

        // Зачувување во база
        financialRecordRepository.save(financialRecord);

        // Испраќање во Kafka
        kafkaTemplate.send(TOPIC, jsonResponse);
    }

    private FinancialRecord parseJsonToFinancialRecord(String jsonResponse) {
        return new FinancialRecord(); // За сега враќаме празен објект, подоцна ќе го имплементираме JSON парсирањето
    }
}
