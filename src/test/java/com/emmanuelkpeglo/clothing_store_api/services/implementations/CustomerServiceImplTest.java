package com.emmanuelkpeglo.clothing_store_api.services.implementations;

import com.emmanuelkpeglo.clothing_store_api.dao.CustomerRepository;
import com.emmanuelkpeglo.clothing_store_api.exceptions.ResourceNotFoundException;
import com.emmanuelkpeglo.clothing_store_api.models.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {
    List<Customer> customers;
    Customer customer;

    @Mock
    private CustomerRepository customerRepository;

    @Autowired
    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {
        customers = Arrays.asList(
                new Customer(1L, "yoshi", "142", "yoyo", "abq", "yoshiland"),
                new Customer(2L, "lee", "ca345", "lala", "44to", "lalaland")
        );

        customer = new Customer(3L, "zonda", "ca345", "lala", "44to", "lalaland");
    }

    @Test
    @DisplayName("should add a new customer")
    void createCustomer() {
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        assertThat(customerService.createCustomer(customer)).returns(3L, Customer::getId);
        assertThat(customerService.createCustomer(customer)).returns("zonda", Customer::getName);
    }

    @Nested
    @DisplayName("should return all customers")
    class GetCustomers {
        @Test
        @DisplayName("should return an empty list assuming no customer is in the database")
        void shouldReturnAnEmptyList() {
            when(customerRepository.findAll()).thenReturn(new ArrayList<Customer>());

            List<Customer> customers = customerService.getCustomers();

            assertThat(customers.size()).isZero();
        }

        @Test
        @DisplayName("returns a list of two customers")
        void shouldReturnAListOfCustomers() {
            when(customerRepository.findAll()).thenReturn(customers);

            List<Customer> AllCustomers = customerService.getCustomers();

            assertThat(AllCustomers.size()).isEqualTo(2);
            assertThat(AllCustomers).isEqualTo(customers);
            assertThat(AllCustomers.get(0)).returns("yoshi", Customer::getName);
        }
    }

    @Nested
    @DisplayName("should return a customer by id")
    class GetACustomer {
        @Test
        @DisplayName("returns a customer if Id exists")
        void shouldReturnACustomerIfCustomerWithIdExists() {
            Long id = 3L;
            when(customerRepository.findById(id)).thenReturn(Optional.of(customer));

            assertThat(customerService.getCustomerById(id)).isEqualTo(customer);
            assertThat(customerService.getCustomerById(id)).returns(id, Customer::getId);
        }

        @Test
        @DisplayName("throws exception given that id does not exist")
        void shouldThrowAnExceptionIfCustomerDoesNotExist() {
            Long id = 9L;
            when(customerRepository.findById(id)).thenReturn(Optional.empty());

            ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> customerService.getCustomerById(id));
            assertThat(thrown.getMessage()).isEqualTo("Customer with id: " + id + " not found!");
        }
    }

    @Nested
    @DisplayName("updates a customer")
    class UpdateCustomer {
        @Test
        @DisplayName("updates a customer given the customer exists")
        void shouldUpdateCustomerIfExists() {
            Long id = 1L;
            when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customers.get(0)));
            when(customerRepository.save(any(Customer.class))).thenReturn(customers.get(0));

            Customer customerUpdate = new Customer();
            customerUpdate.setPostalCode("565665");
            customerUpdate.setName("wario");
            customerUpdate.setCountry("kuvikiland");
            customerUpdate.setCity("hobun");
            customerUpdate.setAddress("97tyu");

            assertThat(customerService.updateCustomer(id, customerUpdate)).returns(id, Customer::getId);
            assertThat(customerService.updateCustomer(id, customerUpdate)).returns("wario", Customer::getName);
        }

        @Test
        @DisplayName("throws an exception if customer to be updated does not exist")
        void shouldThrowAnExceptionIfCustomerDoesNotExist() {

            Long id = 9L;
            when(customerRepository.findById(id)).thenReturn(Optional.empty());

            Customer customerRequest = new Customer();
            customerRequest.setPostalCode("565665");
            customerRequest.setName("wario");
            customerRequest.setCountry("kuvikiland");
            customerRequest.setCity("hobun");
            customerRequest.setAddress("97tyu");

            ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class,
                    () -> customerService.updateCustomer(id, customerRequest));
            assertThat(thrown.getMessage()).isEqualTo("Customer with id: " + id + " not found!");

        }
    }

    @Nested
    @DisplayName("removes a customer")
    class RemoveCustomer {
        @Test
        @DisplayName("removes a customer if the customer exists")
        void shouldRemoveCustomerIfExists() {
            Long id = 3L;
            when(customerRepository.findById(id)).thenReturn(Optional.of(customer));
            customerService.removeCustomer(id);
            customerService.removeCustomer(id);
            customerService.removeCustomer(id);

            verify(customerRepository, times(3)).delete(customer);
        }

        @Test
        @DisplayName("throws an exception if the customer does not exist")
        void shouldThrowAnExceptionIfCustomerDoesNotExist() {
            Long id = 9L;
            when(customerRepository.findById(id)).thenReturn(Optional.empty());

            ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class,
                    () -> customerService.removeCustomer(id));
            assertThat(thrown.getMessage()).isEqualTo("Customer with id: " + id + " not found!");
        }
    }
}