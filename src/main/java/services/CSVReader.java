package services;

import entities.Investor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVReader {

    public static List<Investor> readInvestors(InputStream inputStream) throws IOException {
        // Store references to investors temporarily
        Map<String, Investor> investorsMap = new HashMap<>();

        CSVParser parser = new CSVParser(new InputStreamReader(inputStream), CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).build());

        /* Read all investors and create an object for each. Store references to the objects with
           their name as the key to allow for adding the products to their lists later */
        for (CSVRecord entry : parser) {
            String name = entry.get(0);
            String product = entry.get(1);

            Investor investor = investorsMap.get(name);
            if (investor == null) {
                investor = new Investor(name);
                investorsMap.put(name, investor);
            }

            investor.addProduct(product);
        }

        // Finally return the complete list of investors
        return new ArrayList<>(investorsMap.values());
    }
}
