package pe.gob.sunat.microservices.curso.customers.service;

import pe.gob.sunat.microservices.curso.customers.dao.AddressDaoImpl;
import pe.gob.sunat.microservices.curso.customers.dao.CustomerDaoImpl;
import pe.gob.sunat.microservices.curso.customers.model.Customer;
import pe.gob.sunat.microservices.curso.orders.client.OrderServiceClient;
import pe.gob.sunat.microservices.curso.customers.service.command.OrderServiceRemoteInvokerCommand;

import java.util.Optional;

public class CustomerService {
  private final CustomerDaoImpl dao;
  private final AddressDaoImpl addressDao;
  private final OrderServiceClient orderServiceClient;
  private final OrderService orderService;

  public CustomerService(CustomerDaoImpl dao, AddressDaoImpl addressDao, OrderService orderService, OrderServiceClient orderServiceClient) {
    this.dao = dao;
    this.addressDao = addressDao;
    this.orderService = orderService;
    this.orderServiceClient = orderServiceClient;  
}

  public Customer create(Customer customer) {
    return dao.create(customer);
  }

  public Optional<Customer> findById(Long id, Boolean includeAddresses) {
    return dao.find(id).map(customer -> {
      if (includeAddresses) {
        customer.setAddresses(addressDao.findByCustomer(id));
      }
      return customer;
    });
  }

  public void delete(Long id) {
    Boolean validatedOrder = orderService.validateOrder(id);

    if (validatedOrder) {
      throw new InvalidCustomerException("No puedes eliminar al cliente. Tiene pedidos relacionados.", id.toString());
    }
    
    dao.delete(id);
  }
}
