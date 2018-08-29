package pe.gob.sunat.microservices.curso.customers.api;

import pe.gob.sunat.microservices.curso.customers.service.OrderService;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/v1/orders")
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource {

  private final OrderService orderService;

  public OrderResource(OrderService orderService) {
    this.orderService = orderService;
  }

}
