package com.ingestiontool.controller;

import com.ingestiontool.service.IngestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class IngestionController {

    @Autowired
    private IngestionService ingestionService;

    @GetMapping("/health")
    public String healthCheck() {
        return "Ingestion Tool Backend is running";
    }

    @PostMapping("/clickhouse/tables")
    public List<String> getClickHouseTables(@RequestBody ClickHouseConfig config) throws Exception {
        return ingestionService.getClickHouseTables(config.getHost(), config.getPort(), config.getDatabase(), config.getUser(), config.getJwtToken());
    }

    @PostMapping("/clickhouse/columns")
    public List<String> getClickHouseColumns(@RequestBody ClickHouseTableRequest request) throws Exception {
        return ingestionService.getClickHouseColumns(request.getHost(), request.getPort(), request.getDatabase(), request.getUser(), request.getJwtToken(), request.getTable());
    }

    @PostMapping("/flatfile/columns")
    public List<String> getFlatFileColumns(@RequestBody FlatFileConfig config) throws Exception {
        return ingestionService.getFlatFileColumns(config.getFileName(), config.getDelimiter().charAt(0));
    }

    @PostMapping("/ingest")
    public String startIngestion(@RequestBody IngestionRequest request) {
        // TODO: Implement ingestion logic
        return "Ingestion started (stub)";
    }

    // DTO classes for request bodies

    public static class ClickHouseConfig {
        private String host;
        private String port;
        private String database;
        private String user;
        private String jwtToken;

        // getters and setters
        public String getHost() { return host; }
        public void setHost(String host) { this.host = host; }
        public String getPort() { return port; }
        public void setPort(String port) { this.port = port; }
        public String getDatabase() { return database; }
        public void setDatabase(String database) { this.database = database; }
        public String getUser() { return user; }
        public void setUser(String user) { this.user = user; }
        public String getJwtToken() { return jwtToken; }
        public void setJwtToken(String jwtToken) { this.jwtToken = jwtToken; }
    }

    public static class ClickHouseTableRequest extends ClickHouseConfig {
        private String table;

        public String getTable() { return table; }
        public void setTable(String table) { this.table = table; }
    }

    public static class FlatFileConfig {
        private String fileName;
        private String delimiter;

        public String getFileName() { return fileName; }
        public void setFileName(String fileName) { this.fileName = fileName; }
        public String getDelimiter() { return delimiter; }
        public void setDelimiter(String delimiter) { this.delimiter = delimiter; }
    }

    public static class IngestionRequest {
        private String source;
        private String target;
        private ClickHouseConfig clickHouseConfig;
        private FlatFileConfig flatFileConfig;
        private List<String> selectedColumns;

        public String getSource() { return source; }
        public void setSource(String source) { this.source = source; }
        public String getTarget() { return target; }
        public void setTarget(String target) { this.target = target; }
        public ClickHouseConfig getClickHouseConfig() { return clickHouseConfig; }
        public void setClickHouseConfig(ClickHouseConfig clickHouseConfig) { this.clickHouseConfig = clickHouseConfig; }
        public FlatFileConfig getFlatFileConfig() { return flatFileConfig; }
        public void setFlatFileConfig(FlatFileConfig flatFileConfig) { this.flatFileConfig = flatFileConfig; }
        public List<String> getSelectedColumns() { return selectedColumns; }
        public void setSelectedColumns(List<String> selectedColumns) { this.selectedColumns = selectedColumns; }
    }
}
