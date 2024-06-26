package com.example.backendpensionat.IntegrationTest;
import com.example.backendpensionat.DTO.BlacklistDTO;
import com.example.backendpensionat.DTO.BlacklistDetailedDTO;
import com.example.backendpensionat.PropertiesConfigs.IntegrationPropertiesConfig;
import com.example.backendpensionat.Services.BlacklistService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class BlacklistServiceIntegrationTest {

    @Autowired
    private BlacklistService sut;

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    IntegrationPropertiesConfig integrationPropertiesConfig;

    @Test
    public void whenCheckBlacklistWithValidEmail_thenShouldReturnDetailedDTO() {
        BlacklistDetailedDTO mockResponse = new BlacklistDetailedDTO();
        mockResponse.setEmail("test@example.com");
        mockResponse.setOk(true);
        when(restTemplate.getForObject(integrationPropertiesConfig.getCheckBlacklistUrl() + "/" +  mockResponse.getEmail(), BlacklistDetailedDTO.class))
                .thenReturn(mockResponse);

        BlacklistDetailedDTO result = sut.checkBlackList("test@example.com");

        assertThat(result).isNotNull();
        assertEquals("OK", result.statusText);
        assertTrue(result.ok);
    }

    @Test
    public void getBlackListRemote() throws IOException {
        assertDoesNotThrow(() -> sut.getBlacklist(false));
        List<BlacklistDTO> result = sut.getBlacklist(false);

        assertFalse(result.isEmpty());
        assertNotNull(result.get(0).externalID);
        assertNotNull(result.get(0).email);
        assertNotNull(result.get(0).name);
        assertNotNull(result.get(0).group);
        assertNotNull(result.get(0).created);
    }

    @Test
    public void getBlackListLocal() throws IOException {
        assertDoesNotThrow(() -> sut.getBlacklist(true));
        List<BlacklistDTO> result = sut.getBlacklist(true);

        assertFalse(result.isEmpty());
        assertEquals(3, result.size());
        assertEquals("stefa@aaasdadsdsd.com", result.get(2).email);
        assertEquals("Karlsson", result.get(2).name);
        assertEquals("stefan", result.get(2).group);
        assertFalse(result.get(2).ok);
    }

    @Test
    public void addCustomerToBlacklist() throws IOException {
        assertDoesNotThrow(() -> sut.getBlacklist(false));
        List<BlacklistDTO> result = sut.getBlacklist(false);

        int sizeBefore = result.size();

        String randomNumber = String.valueOf((int) (Math.random() * 10000) + 1000);

        sut.addCustomerToBlacklist("1111@2222." + randomNumber, "addCustomerToBlacklistTest");
        result = sut.getBlacklist(false);

        int sizeAfter = result.size();

        assertEquals(sizeBefore + 1, sizeAfter);
        assertEquals("addCustomerToBlacklistTest", result.get(sizeAfter - 1).name);
        assertEquals("1111@2222." + randomNumber, result.get(sizeAfter - 1).email);
    }
}