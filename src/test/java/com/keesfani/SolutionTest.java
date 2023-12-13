package com.keesfani;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class SolutionTest {
    private Solution solution;
    private ObjectMapper mapper;

    @BeforeEach
    public void setUp() {
        solution = new Solution();
        mapper = new ObjectMapper();
    }

    @Test
    @Disabled
    public void testGetCountryInAsiaWithMostNeighboursFromDifferentRegionWithHTTP() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode countryJSON = mapper.readTree(URI.create("https://restcountries.com/v3.1/all?fields=name,area,population,region,borders,cca3,continents").toURL());

        String country = solution.getCountryInAsiaWithMostNeighboursFromDifferentRegion(countryJSON);
        assertEquals("Russia", country);
    }

    @Test
    @Disabled
    public void testgetCountryNamesSortedByDensityWithHTTP() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode countryJSON = mapper.readTree(URI.create("https://restcountries.com/v3.1/all?fields=name,area,population,region,borders,cca3,continents").toURL());

        List<String> countries = solution.getCountryNamesSortedByDensity(countryJSON);
        assertIterableEquals(List.of("Macau", "Monaco", "Singapore", "Hong Kong", "Gibraltar"), countries.subList(0, 5));
    }

    @Test
    public void testGetCountryInAsiaWithMostNeighboursFromDifferentRegion() throws JsonProcessingException {
        //language=JSON
        String json = """
                [
                  {
                    "name": {
                      "common": "Country1"
                    },
                    "cca3": "C1",
                    "region": "Asia",
                    "borders": [
                      "C3"
                    ],
                    "continents": [
                      "Asia"
                    ]
                  },
                  {
                    "name": {
                      "common": "Country2"
                    },
                    "cca3": "C2",
                    "region": "Europe",
                    "borders": [
                      "C1",
                      "C3"
                    ],
                    "continents": [
                      "Europe"
                    ]
                  },
                  {
                    "name": {
                      "common": "Country3"
                    },
                    "cca3": "C3",
                    "region": "Asia",
                    "borders": [
                      "C1",
                      "C2"
                    ],
                    "continents": [
                      "Asia"
                    ]
                  }
                ]
                """;

        JsonNode countries = mapper.readTree(json);
        String country = solution.getCountryInAsiaWithMostNeighboursFromDifferentRegion(countries);
        assertEquals("Country3", country);
    }

    @Test
    public void testGetCountryNamesSortedByDensity() throws JsonProcessingException {
        //language=JSON
        String json = """
                [
                  {
                    "name": {
                      "common": "Country1"
                    },
                    "area": 100,
                    "population": 1000
                  },
                  {
                    "name": {
                      "common": "Country0"
                    },
                    "area": 10,
                    "population": 1000
                  },
                  
                  {
                    "name": {
                      "common": "Country2"
                    },
                    "area": 200,
                    "population": 1000
                  }
                ]
                """;

        JsonNode countries = mapper.readTree(json);
        List<String> sortecCountries = solution.getCountryNamesSortedByDensity(countries);
        assertIterableEquals(List.of("Country0", "Country1", "Country2"), sortecCountries);
    }

    @Test
    public void testCalculatePopulationDensity() throws JsonProcessingException {
        //language=JSON
        String json = """
                {
                  "name": {
                    "common": "Country1"
                  },
                  "area": 100,
                  "population": 1000
                }
                """;

        JsonNode country = mapper.readTree(json);
        int density = solution.calculatePopulationDensity(country);
        assertEquals(10, density);
    }

    @Test
    public void testExtractCodesWithAsiaRegion() throws JsonProcessingException {
        //language=JSON
        String json = """
                [
                  {
                    "name": {
                      "common": "Country1"
                    },
                    "region": "Asia",
                    "cca3": "C1"
                  },
                  {
                    "name": {
                      "common": "Country2"
                    },
                    "region": "Europe",
                    "cca3": "C2"
                  },
                  {
                    "name": {
                      "common": "Country3"
                    },
                    "region": "Asia",
                    "cca3": "C3"
                  }
                ]
                """;

        JsonNode countries = mapper.readTree(json);
        Set<String> codes = solution.extractCodesWithAsiaRegion(countries);
        assertEquals(2, codes.size());
        List.of("C1", "C3").forEach(code -> assertTrue(codes.contains(code)));
    }

    @Test
    public void testCountryIsInAsia() throws JsonProcessingException {
        //language=JSON
        String json = """
                {
                  "name": {
                    "common": "Country1"
                  },
                  "region": "Asia",
                  "cca3": "C1",
                  "continents": [
                    "Europe",
                    "Asia"
                  ]
                }
                """;

        JsonNode country = mapper.readTree(json);
        assertTrue(solution.countryIsInAsia(country));
    }

    @Test
    public void testCountryIsNotInAsia() throws JsonProcessingException {
        //language=JSON
        String json = """
                {
                  "name": {
                    "common": "Country1"
                  },
                  "region": "Europe",
                  "cca3": "C1",
                  "continents": [
                    "Europe"
                  ]
                }
                """;

        JsonNode country = mapper.readTree(json);
        assertFalse(solution.countryIsInAsia(country));
    }

    @Test
    public void testStreamJsonNode() throws JsonProcessingException {
        //language=JSON
        String json = """
                [
                  {
                    "name": {
                      "common": "Country1"
                    },
                    "region": "Asia",
                    "cca3": "C1",
                    "continents": [
                      "Europe",
                      "Asia"
                    ]
                  },
                  {
                    "name": {
                      "common": "Country2"
                    },
                    "region": "Europe",
                    "cca3": "C2",
                    "continents": [
                      "Europe"
                    ]
                  }
                ]
                """;

        JsonNode countries = mapper.readTree(json);

        ArrayNode arrayNode = JsonNodeFactory.instance.arrayNode();
        solution.streamJsonNode(countries).forEach(arrayNode::add);

        assertEquals(countries, arrayNode);
    }
}