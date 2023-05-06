package uk.landbay;

import entities.Investor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.CSVReader;

import java.io.IOException;
import java.util.List;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        logger.info("The program has started");
        try {
            Main.runAlgorithm();
        }
        finally {
            logger.info("The program has ended");
        }
    }

    private static void runAlgorithm() {
        try {
            List<Investor> investors = CSVReader.readInvestors(Main.class.getResourceAsStream("/funded_products_by_funder.csv"));
            for(Investor inv : investors) {
                System.out.println(inv.getName());
            }
        }
        catch (IOException exception) {
            logger.error("An exception occurred when reading the investors file", exception);
        }
        catch (Exception exception) {
            logger.error("An unknown error occurred", exception);
        }

    }
}