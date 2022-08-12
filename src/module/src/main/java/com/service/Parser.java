package com.service;

import com.exception.file.read.InvalidLineException;
import com.model.product.AbstractProduct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShopService.class);

    protected List<String> readLinesFromCSV(Path path) throws InvalidLineException {


        String line;
        List<String> lines = new ArrayList<>();
        BufferedReader bufferedReader;

        try {
            bufferedReader = new BufferedReader(new FileReader(String.valueOf(path.getFileName())));
        } catch (FileNotFoundException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }

        while (true) {
            try {
                if ((line = bufferedReader.readLine()) == null) {
                    break;
                }
                lines.add(line);
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
                throw new InvalidLineException(toString());
            }
        }
        return lines;
    }

    public List<AbstractProduct> parseCSV() throws InvalidLineException {
        Path path = Paths.get("src/main/resources/ProductTable.csv");
        if (!Files.exists(path)) {
            throw new IllegalArgumentException();
        }

        final int STRING_WITH_NAMES_OF_COLUMN = 0;
        String[] fields = readLinesFromCSV(path).get(STRING_WITH_NAMES_OF_COLUMN).split(",");

        final int FIRST_LINE_WITH_SPECIFICATIONS = 1;
        Map<String, String> result = new HashMap<>();
        List<AbstractProduct> products = new ArrayList<>();
        readLinesFromCSV(path).stream()
                .map(l -> l.split(","))
                .forEach(values -> {
                    for (int i = FIRST_LINE_WITH_SPECIFICATIONS; i < values.length; i++) {
                        result.put(fields[i], values[i]);
                    }
                    products.add(ProductFactory.createProductFromMap(result));
                });
        return products;
    }
}
