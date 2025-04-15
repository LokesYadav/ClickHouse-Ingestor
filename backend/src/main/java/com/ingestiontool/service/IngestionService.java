package com.ingestiontool.service;

import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVParserBuilder;
import org.springframework.stereotype.Service;

@Service
public class IngestionService {

    public List<String> getClickHouseTables(String host, String port, String database, String user, String jwtToken) throws Exception {
        // TODO: Implement ClickHouse connection with JWT and fetch tables
        // For now, return mock data
        List<String> tables = new ArrayList<>();
        tables.add("uk_price_paid");
        tables.add("ontime");
        return tables;
    }

    public List<String> getClickHouseColumns(String host, String port, String database, String user, String jwtToken, String table) throws Exception {
        // TODO: Implement fetching columns for a table from ClickHouse
        // For now, return mock columns
        List<String> columns = new ArrayList<>();
        columns.add("column1");
        columns.add("column2");
        columns.add("column3");
        return columns;
    }

    public List<String> getFlatFileColumns(String fileName, char delimiter) throws Exception {
        try (CSVReader reader = new CSVReaderBuilder(new FileReader(fileName))
                .withCSVParser(new CSVParserBuilder().withSeparator(delimiter).build())
                .build()) {
            String[] header = reader.readNext();
            if (header == null) {
                throw new Exception("Empty file or unable to read header");
            }
            List<String> columns = new ArrayList<>();
            for (String col : header) {
                columns.add(col);
            }
            return columns;
        }
    }

    public int ingestClickHouseToFlatFile(String host, String port, String database, String user, String jwtToken, String table, List<String> columns, String fileName, char delimiter) throws Exception {
        // TODO: Implement ClickHouse connection with JWT and query data
        // For now, mock writing CSV with dummy data
        try (CSVWriter writer = new CSVWriter(new FileWriter(fileName), delimiter, CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END)) {
            writer.writeNext(columns.toArray(new String[0]));
            // Mock data rows
            writer.writeNext(new String[]{"value1", "value2", "value3"});
            writer.writeNext(new String[]{"value4", "value5", "value6"});
        }
        return 2; // number of records written (excluding header)
    }

    public int ingestFlatFileToClickHouse(String fileName, char delimiter, String host, String port, String database, String user, String jwtToken, String table, List<String> columns) throws Exception {
        int count = 0;
        try (CSVReader reader = new CSVReaderBuilder(new FileReader(fileName))
                .withCSVParser(new CSVParserBuilder().withSeparator(delimiter).build())
                .build()) {
            String[] header = reader.readNext(); // skip header
            String[] line;
            while ((line = reader.readNext()) != null) {
                count++;
                // TODO: Insert line into ClickHouse table
            }
        }
        return count;
    }

    public void processCsv(String fileName, char delimiter) throws Exception {
        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(fileName))
                .withCSVParser(new CSVParserBuilder().withSeparator(delimiter).build())
                .build()) {
            // Process CSV data
        }
    }
}
