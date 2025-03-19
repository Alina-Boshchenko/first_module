package ru.boshchenko.object_mapper.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.boshchenko.object_mapper.model.Customer;
import ru.boshchenko.object_mapper.repo.CustomerRepo;
import ru.boshchenko.object_mapper.service.CustomerService;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepo customerRepo;

    @Override
    public Customer create(Customer customer) {
        return customerRepo.save(customer);
    }
}
