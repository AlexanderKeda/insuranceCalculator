package org.javaguru.travel.insurance.rest.internal;

import org.javaguru.travel.insurance.rest.common.JsonFileReader;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
class TravelGetAgreementRestControllerTest {

    @Autowired
    JsonFileReader jsonFileReader;

    @Autowired
    private MockMvc mockMvc;

    private static final int NUMBER_OF_TEST_CASES = 3;

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
        String requestPath = "/rest/internal/" + testCase + "/requestUri.txt";
        String expectedResponsePath = "/rest/internal/" + testCase + "/response.json";
        performAndCheck(requestPath, expectedResponsePath);
    }

    private void performAndCheck(String requestPath, String expectedResponsePath) throws Exception {
        String requestUri = jsonFileReader.readJsonFromFile(requestPath);

        String expectedResponseJson = jsonFileReader.readJsonFromFile(expectedResponsePath);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(requestUri))
                .andExpect(status().isOk())
                .andReturn();
        String jsonResponse = result.getResponse().getContentAsString();
        assertJson(jsonResponse)
                .where()
                .keysInAnyOrder()
                .arrayInAnyOrder()
                .isEqualTo(expectedResponseJson);
    }

}