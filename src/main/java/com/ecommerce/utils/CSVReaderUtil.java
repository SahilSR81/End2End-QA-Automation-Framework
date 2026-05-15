package com.ecommerce.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CSVReaderUtil {
    
    /**
     * Reads a CSV file and converts it into a list of maps, where each map
     * represents a row from the CSV file mapped by its header column names.
     *
     * @param filePath The absolute or relative path to the CSV file.
     * @return List of Maps containing the read data.
     * @throws RuntimeException If the file is not found or cannot be read.
     */
    public static List<Map<String, String>> readCSV(String filePath) {
        List<Map<String, String>> data = new ArrayList<>();
        try (Reader reader = new FileReader(filePath);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).build())) {
            
            for (CSVRecord csvRecord : csvParser) {
                data.add(csvRecord.toMap());
            }
        } catch (FileNotFoundException e) {
            LoggerUtil.error("CSV file not found: " + filePath, e);
            throw new RuntimeException("CSV file not found: " + filePath, e);
        } catch (IOException e) {
            LoggerUtil.error("Error reading CSV file: " + filePath, e);
            throw new RuntimeException("Error reading CSV file: " + filePath, e);
        }
        return data;
    }
}
