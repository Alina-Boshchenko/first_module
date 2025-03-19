package ru.boshchenko.object_mapper.service;

import org.springframework.stereotype.Service;
import ru.boshchenko.object_mapper.model.Customer;

@Service
public interface CustomerService {

    Customer create(Customer customer);

}
