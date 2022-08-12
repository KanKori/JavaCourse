package com.service;

import com.exception.file.read.InvalidLineException;
import com.model.product.AbstractProduct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShopService.class);

    protected List<String> readLinesFromCSV() {
        String line;
        List<String> lines = new ArrayList<>();
        String csv = ".\\src\\module\\src\\main\\resources\\products.csv";
        BufferedReader bufferedReader;
        Pattern lineCSV = Pattern.compile("^((?:[^,]+,\\s*){6})[^,]+");
        try {
            bufferedReader = new BufferedReader(new FileReader(csv));
            while ((line = bufferedReader.readLine()) != null) {
                Matcher matcher = lineCSV.matcher(line);
                if (matcher.find()) {
                    lines.add(line);
                } else {
                    try {
                        throw new InvalidLineException(toString());
                    } catch (InvalidLineException e) {
                        LOGGER.error(e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return lines;
    }

    public List<AbstractProduct> parseCSV() throws InvalidLineException {
        Path path = Paths.get(".\\src\\module\\src\\main\\resources\\products.csv");
        final int STRING_WITH_NAMES_OF_COLUMN = 0;
        String[] columns = readLinesFromCSV().get(STRING_WITH_NAMES_OF_COLUMN).split(",");

        final int FIRST_SPECIFICATION = 0;
        List<AbstractProduct> products = new ArrayList<>();
        Map<String, String> result = new HashMap<>();
        List<String> values = readLinesFromCSV();
        values.stream()
                .skip(1)
                .map(splitChar -> splitChar.split(","))
                .forEach(value -> {
                    for (int i = FIRST_SPECIFICATION; i < value.length; i++) {
                        result.put(columns[i], value[i]);
                    }
                    products.add(ProductFactory.createProductFromMap(result));
                });
        return products;
    }
}
