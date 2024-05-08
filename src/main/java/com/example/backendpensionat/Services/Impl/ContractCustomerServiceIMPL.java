package com.example.backendpensionat.Services.Impl;

import com.example.backendpensionat.DTO.ContractCustomerDTO;
import com.example.backendpensionat.DTO.ContractCustomerDetailedDTO;
import com.example.backendpensionat.Models.ContractCustomer;
import com.example.backendpensionat.Repos.ContractCustomerRepo;
import com.example.backendpensionat.Services.ContractCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ContractCustomerServiceIMPL implements ContractCustomerService {

    @Autowired
    ContractCustomerRepo contractCustomerRepo;

    @Override
    public ContractCustomer saveContractCustomer(ContractCustomerDTO cCustomer) {
        return contractCustomerRepo.save(detailToCms(cCustomer));
    }

    @Override
    public List<ContractCustomerDTO> listAllContractCustomers() {
        return contractCustomerRepo.findAll().stream().map(this::CmsToDTO).toList();
    }


    @Override
    public String removeCCustomer(ContractCustomerDetailedDTO cCustomer) {
        /*if(cCustomer.getBookings() != null){
            contractCustomerRepo.delete();
        }*/
        return "";
    }


    public ContractCustomerDTO CmsToDTO(ContractCustomer ContractCustomer) {
        return ContractCustomerDTO.builder()
                .internalId(ContractCustomer.id)
                .externalId(ContractCustomer.externalId)
                .companyName(ContractCustomer.companyName)
                .contactName(ContractCustomer.contactName)
                .contactTitle(ContractCustomer.contactTitle)
                .streetAddress(ContractCustomer.streetAddress)
                .city(ContractCustomer.city)
                .postalCode(ContractCustomer.postalCode)
                .country(ContractCustomer.country)
                .phone(ContractCustomer.phone)
                .fax(ContractCustomer.fax).build();
    }

    public ContractCustomer detailToCms(ContractCustomerDTO contractCustomerDTO) {
        List<ContractCustomer> contractCustomers = contractCustomerRepo.findAll();

        ContractCustomer matchingCustomer = contractCustomers.stream().filter(customer ->
                customer.externalId.equals(contractCustomerDTO.externalId)).findFirst().orElse(new ContractCustomer());

            return ContractCustomer.builder()
                    .id(matchingCustomer.id)
                    .externalId(contractCustomerDTO.externalId)
                    .companyName(contractCustomerDTO.companyName)
                    .contactName(contractCustomerDTO.contactName)
                    .contactTitle(contractCustomerDTO.contactTitle)
                    .streetAddress(contractCustomerDTO.streetAddress)
                    .city(contractCustomerDTO.city)
                    .postalCode(contractCustomerDTO.postalCode)
                    .country(contractCustomerDTO.country)
                    .phone(contractCustomerDTO.phone)
                    .fax(contractCustomerDTO.fax).build();
    }
}
