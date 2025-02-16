package mk.ukim.finki.dians_dopolnitelno.model;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class FinancialRecord {

    @JsonProperty("Meta Data")
    private MetaData metaData;

    @JsonProperty("Time Series (5min)")
    private Map<String, TimeSeriesData> timeSeriesData;

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    public Map<String, TimeSeriesData> getTimeSeriesData() {
        return timeSeriesData;
    }

    public void setTimeSeriesData(Map<String, TimeSeriesData> timeSeriesData) {
        this.timeSeriesData = timeSeriesData;
    }


    public static class MetaData {
        @JsonProperty("1. Information")
        private String information;

        @JsonProperty("2. Symbol")
        private String symbol;

        @JsonProperty("3. Last Refreshed")
        private String lastRefreshed;

        @JsonProperty("4. Interval")
        private String interval;

        @JsonProperty("5. Output Size")
        private String outputSize;

        @JsonProperty("6. Time Zone")
        private String timeZone;

        // Getters and Setters
    }

    public static class TimeSeriesData {
        @JsonProperty("1. open")
        private String open;

        @JsonProperty("2. high")
        private String high;

        @JsonProperty("3. low")
        private String low;

        @JsonProperty("4. close")
        private String close;

        @JsonProperty("5. volume")
        private String volume;

        // Getters and Setters
    }
}
