package com.service.parser;

import com.exception.file.read.InvalidLineException;
import com.model.product.AbstractProduct;
import com.service.shop.ShopService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParserCSVTest {

    private ParserCSV target;
    private ShopService shopService;
    private List<AbstractProduct> readLines;
    private final List<String> linesFromCVS = new ArrayList<>();

    private final String csv = ".\\src\\main\\resources\\products.csv";

    @BeforeEach
    void setUp() {
        double sumLimit = 2000;
        shopService = new ShopService(csv, sumLimit);
        target = new ParserCSV();
    }

    @Test
    void readLinesFromCSV() {
        linesFromCVS.add("type,series,model,diagonal,screen type,country,price");
        linesFromCVS.add("Telephone,SuperChinaPhone,S-TELL,none,,none,0");
        List<String> parsedLines = target.readLinesFromCSV(csv);
        assertEquals("type,series,model,diagonal,screen type,country,price", parsedLines.get(0));
    }

    @Test
    void parseCSV() throws InvalidLineException {
        readLines = target.parseCSV(csv);
        assertNotEquals(null, readLines);
    }
}