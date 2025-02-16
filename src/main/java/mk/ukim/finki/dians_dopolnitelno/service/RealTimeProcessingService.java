package mk.ukim.finki.dians_dopolnitelno.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class RealTimeProcessingService {

    @KafkaListener(topics = "financial-data", groupId = "financial-data-group")
    public void processFinancialData(String message) {
        System.out.println("Received financial data: " + message);

        // Овде можеш да додадеш обработка на податоците (филтрирање, агрегација, трансформација итн.)
    }
}
