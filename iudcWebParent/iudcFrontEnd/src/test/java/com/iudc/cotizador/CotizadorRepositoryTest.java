package com.iudc.cotizador;

import com.iudc.common.entity.Customer;
import com.iudc.common.entity.product.ProductoCotizador;
import com.iudc.customer.CustomerRepository;
import org.junit.jupiter.api.Test;
import java.util.Date;
import java.util.Calendar;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class CotizadorRepositoryTest {
    
    @Autowired CotizadorRepository cotizadorRepo;
    @Autowired CustomerRepository customerRepo;
    
    
    @Test
    public void guardarCotizadorProducto(){
        
        
    Calendar calendar = Calendar.getInstance();
    Date orderTime = calendar.getTime();
    String superficieAplicacion = "Neumaticos";
    String impresion = "Sin impresión";
    String forma = "Continua"; 
    String medidas = "Ancho: 4 cm Largo: 20 m"; 
    String papel = "PCB Hot melt"; 
    String color = "#6afbf9";
    String acabado = "Barniz brillante";
    String salida = "NA";
    String presentacion = "Rollo con centro de 2.54 cm - 1 in";
    Double precioMillar = 245.0;
    Double minimoFabricacion = 24.0;
    Double cantidadPedido = 24.0;
    Customer customerId = customerRepo.findByEmail("eliasvillalpando01@gmail.com");
    Integer customerPedidoCotizacion = customerId.getId();
    
    
    ProductoCotizador productoCotizacion = new ProductoCotizador();
    productoCotizacion.setId(customerPedidoCotizacion);
    productoCotizacion.setOrderTime(orderTime);
    productoCotizacion.setSuperficieAplicacion(superficieAplicacion);
    productoCotizacion.setImpresion(impresion);
    productoCotizacion.setForma(forma);
    productoCotizacion.setMedidas(medidas);
    productoCotizacion.setPapel(papel);
    productoCotizacion.setColor(color);
    productoCotizacion.setAcabado(acabado);
    productoCotizacion.setSalida(salida);
    productoCotizacion.setPresentacion(presentacion);
    productoCotizacion.setPrecioMillar(precioMillar);
    productoCotizacion.setMinimoFabricacion(minimoFabricacion);
    productoCotizacion.setCantidadPedido(cantidadPedido);
    
    
    ProductoCotizador savedProductoCotizador = cotizadorRepo.save(productoCotizacion);
    assertThat(savedProductoCotizador).isNotNull();
        
    
    }
    
   
    
    
}
