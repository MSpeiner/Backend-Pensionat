package com.example.backendpensionat.UnitTest;

import com.example.backendpensionat.DTO.ContractCustomerDTO;
import com.example.backendpensionat.Models.ContractCustomer;
import com.example.backendpensionat.Repos.ContractCustomerRepo;
import com.example.backendpensionat.Services.ContractCustomerService;
import com.example.backendpensionat.Services.Impl.ContractCustomerServiceIMPL;
import org.mockito.Mockito;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContractCustomerServiceUnitTest {
    private final ContractCustomerRepo contractCustomerRepo = mock(ContractCustomerRepo.class);
    URL localUrl = getClass().getClassLoader().getResource("./XmlJsonFiles/contractCustomers.xml");
    ContractCustomerService sut;

    @BeforeEach()
    void setUp() {
        sut = new ContractCustomerServiceIMPL(contractCustomerRepo);
    }

    @Test
    void whenGetContractCustomersShouldMapCorrectly() throws IOException {

        List<ContractCustomerDTO> result = sut.getContractCustomersFromXML(localUrl);

        assertEquals(3, result.size());
        assertEquals("Persson Kommanditbolag", result.getFirst().companyName);
        assertEquals("Maria Åslund", result.getFirst().contactName);
        assertEquals("gardener", result.getFirst().contactTitle);
        assertEquals("Anderssons Gata 259", result.getFirst().streetAddress);
        assertEquals("Kramland", result.getFirst().city);
        assertEquals(60843, result.getFirst().postalCode);
        assertEquals("Sverige", result.getFirst().country);
        assertEquals("076-340-7143", result.getFirst().phone);
        assertEquals("1500-16026", result.getFirst().fax);
    }

    @Test
    void getAndSaveContractCustomersShouldInsertNewRecords() throws IOException {
        when(contractCustomerRepo.findContractCustomerByExternalId(Mockito.anyLong())).thenReturn(Optional.empty());

        sut.getAndSaveContractCustomers(true);

        verify(contractCustomerRepo, times(3)).save(argThat(customer -> customer.id == null));
    }

    @Test
    void getAndSaveContractCustomersShouldUpdateExistingRecords() throws IOException {
        ContractCustomer existingCustomer = new ContractCustomer();
        existingCustomer.id = 1L;
        existingCustomer.setExternalId(1L);

        when(contractCustomerRepo.findContractCustomerByExternalId(Mockito.anyLong())).thenReturn(Optional.empty());
        when(contractCustomerRepo.findContractCustomerByExternalId(1L)).thenReturn(Optional.of(existingCustomer));

        sut.getAndSaveContractCustomers(true);

        verify(contractCustomerRepo, times(2)).save(argThat(customer -> customer.id == null));
        verify(contractCustomerRepo, times(1)).save(argThat(customer -> customer.externalId == 1L));
    }

}
