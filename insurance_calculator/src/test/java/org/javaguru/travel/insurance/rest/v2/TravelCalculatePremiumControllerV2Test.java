package org.javaguru.travel.insurance.rest.v2;

import org.javaguru.travel.insurance.rest.common.JsonFileReader;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.org.webcompere.modelassert.json.JsonAssertions.assertJson;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
        "medical.risk.limit.level.enabled=true",
        "medical.risk.age.coefficient.enabled=true"
})
class TravelCalculatePremiumControllerV2Test {

    @Autowired
    JsonFileReader jsonFileReader;

    @Autowired
    private MockMvc mockMvc;

    private static final int NUMBER_OF_TEST_CASES = 11;

    static Stream<String> testCasesStream() {
        return IntStream.rangeClosed(1, NUMBER_OF_TEST_CASES)
                .mapToObj(i -> String.format("test_case_%02d", i));
    }

    @ParameterizedTest
    @MethodSource("testCasesStream")
    void executeTestCases(String testCase) throws Exception {
        runTestCase(testCase);
    }

    private void runTestCase(String testCase) throws Exception {
        String requestPath = "/rest/v2/" + testCase + "/request.json";
        String expectedResponsePath = "/rest/v2/" + testCase + "/response.json";
        performAndCheck(requestPath, expectedResponsePath);
    }

    private void performAndCheck(String requestPath, String expectedResponsePath) throws Exception {
        String requestJson = jsonFileReader.readJsonFromFile(requestPath);

        String expectedResponseJson = jsonFileReader.readJsonFromFile(expectedResponsePath);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/insurance/travel/api/v2/")
                        .content(requestJson)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String jsonResponse = result.getResponse().getContentAsString();
        assertJson(jsonResponse)
                .where()
                .keysInAnyOrder()
                .arrayInAnyOrder()
                .at("/uuid").matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$")
                .isEqualTo(expectedResponseJson);
    }
}