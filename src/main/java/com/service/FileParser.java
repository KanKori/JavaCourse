package com.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileParser.class);

    private List<String> readLines(InputStream inputStream) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return lines;
    }

    public Map<String, String> parseJSONToMap(InputStream inputStream) {
        List<String> lines = readLines(inputStream);
        Map<String, String> map = new HashMap<>();
        Pattern pattern = Pattern.compile("\"\\w+\": \".*\"");
        lines.stream()
                .map(String::trim)
                .forEach(line -> {
                    Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        String field = matcher.group().substring(1, line.indexOf(":") - 1);
                        String value = matcher.group().substring(line.indexOf(":") + 3, line.lastIndexOf("\""));
                        map.put(field, value);
                    }
                });
        return map;
    }

    public Map<String, String> parseXMLToMap(InputStream inputStream) {
        List<String> lines = readLines(inputStream);
        Map<String, String> map = new HashMap<>();
        Pattern paramsPattern = Pattern.compile(">(.*)<(.*)");
        Pattern atributePattern = Pattern.compile("\\s\\w*\\b.*\".*\">");
        lines.stream()
                .map(String::trim)
                .forEach(line -> {
                    Matcher matcher = paramsPattern.matcher(line);
                    if (matcher.find()) {
                        String paramsField = matcher.group().substring(line.indexOf("/") + 1 - matcher.start(), line.lastIndexOf(">") - matcher.start());
                        String paramsValue = matcher.group().substring(line.indexOf(">") + 1 - matcher.start(), line.lastIndexOf("<") - matcher.start());
                        map.put(paramsField, paramsValue);
                    }
                    matcher = atributePattern.matcher(line);
                    if (matcher.find()) {
                        String atributeField = matcher.group().substring(1, line.indexOf("=") - matcher.start());
                        String atributeValue = matcher.group().substring(line.indexOf("\"") + 1 - matcher.start(), line.lastIndexOf("\"") - matcher.start());
                        map.put(atributeField, atributeValue);
                    }
                });
        return map;
    }
}
