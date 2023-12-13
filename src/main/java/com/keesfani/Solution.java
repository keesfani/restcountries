package com.keesfani;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Solution
{
    public static void main( String[] args ) throws IOException {
        new Solution().run();
    }

    public void run() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode countryJSON = mapper.readTree(URI.create("https://restcountries.com/v3.1/all?fields=name,area,population,region,borders,cca3,continents").toURL());

        System.out.println("Countries sorted by population density: "
                + String.join(", ", getCountryNamesSortedByDensity(countryJSON)));
        System.out.println("Country in Asia with most neighbours from different region: "
                + getCountryInAsiaWithMostNeighboursFromDifferentRegion(countryJSON));
    }

    public List<String> getCountryNamesSortedByDensity(JsonNode countries) {
        return streamJsonNode(countries)
                .sorted(Comparator.comparing(this::calculatePopulationDensity).reversed())
                .map(country -> country.get("name").get("common").asText())
                .toList();
    }

    public String getCountryInAsiaWithMostNeighboursFromDifferentRegion(JsonNode countries) {
        Set<String> asianRegionCodes = extractCodesWithAsiaRegion(countries);

        return streamJsonNode(countries)
                .filter(this::countryIsInAsia)
                .max(Comparator.comparing(country -> streamJsonNode(country.get("borders"))
                        .filter(border -> !asianRegionCodes.contains(border.asText()))
                        .count()))
                .map(country -> country.get("name").get("common").asText())
                .orElse("No Asian country found.");
    }

    int calculatePopulationDensity(JsonNode country) {
        return country.get("area").asInt() > 0
                ? country.get("population").asInt() / country.get("area").asInt()
                : 0;
    }

    Set<String> extractCodesWithAsiaRegion(JsonNode countries) {
        return streamJsonNode(countries)
                .filter(country -> country.get("region").asText().equals("Asia"))
                .map(country -> country.get("cca3").asText())
                .collect(Collectors.toSet());
    }

    boolean countryIsInAsia(JsonNode country) {
        return streamJsonNode(country.get("continents"))
                .anyMatch(continent -> continent.asText().equals("Asia"));
    }

    Stream<JsonNode> streamJsonNode(JsonNode node) {
        return StreamSupport.stream(node.spliterator(), false);
    }
}
