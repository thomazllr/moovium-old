package com.thomazllr.moovium.commons;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BannedWordsProvider {

    private final ResourceLoader resourceLoader;

    @Value("${banned.words.location}")
    private String bannedWordsLocation;

    private Set<String> banned;

    public BannedWordsProvider(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @PostConstruct
    void load() {
        try {
            Resource resource = resourceLoader.getResource(bannedWordsLocation);

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {

                banned = br.lines()
                        .map(String::trim)
                        .filter(line -> !line.isEmpty() && !line.startsWith("#"))
                        .map(s -> Normalizer.normalize(s, Normalizer.Form.NFKC))
                        .map(String::toLowerCase)
                        .collect(Collectors.toUnmodifiableSet());
            }
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load banned words from: " + bannedWordsLocation, e);
        }
    }

    public boolean containsIn(String text) {
        if (banned == null || banned.isEmpty()) return false;

        String normalizedText = normalizeForComparison(text);

        for (String bannedWord : banned) {
            String normalizedBannedWord = normalizeForComparison(bannedWord);
            if (normalizedText.contains(normalizedBannedWord)) {
                return true;
            }
        }
        return false;
    }

    private String normalizeForComparison(String text) {
        if (text == null) return "";

        return Normalizer.normalize(text, Normalizer.Form.NFKC)
                .toLowerCase()
                .replace("0", "o")
                .replace("1", "i")
                .replace("3", "e")
                .replace("4", "a")
                .replace("5", "s")
                .replace("7", "t")
                .replace("@", "a")
                .replace("$", "s")
                .replaceAll("[._-]", "");
    }
}