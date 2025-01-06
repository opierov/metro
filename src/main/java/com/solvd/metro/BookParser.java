package com.solvd.metro;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class BookParser {

    private static final Logger logger = LogManager.getLogger(BookParser.class);

    public static void main(String[] args) throws IOException {

        File inputFile = new File("src/main/resources/book.txt");
        String text = FileUtils.readFileToString(inputFile, "UTF-8");

        String textLowerCase = text.toLowerCase();

        StringBuilder content = new StringBuilder();
        for (char c : textLowerCase.toCharArray()) {
            if (Character.isLetterOrDigit(c) || Character.isWhitespace(c)) {
                content.append(c);
            }
        }

        String[] words = content.toString().split(" ");

        Set<String> uniqueWords = new HashSet<>();
        for (String word : words) {
            if (!word.isEmpty()) {
                uniqueWords.add(word);
            }
        }

        File outputFile = new File("src/main/resources/unique_words.txt");
        FileUtils.writeStringToFile(outputFile, "Unique words count: " + uniqueWords.size(), "UTF-8");

        logger.info("Unique words count: {}", uniqueWords.size());
        logger.info("Result written to: {}", outputFile.getAbsolutePath());

    }
}