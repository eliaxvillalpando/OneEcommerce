package com.iudc.cotizador;

import com.iudc.common.entity.product.ProductoCotizador;
import org.springframework.data.repository.CrudRepository;


public interface CotizadorRepository extends CrudRepository <ProductoCotizador, Integer> {
    
    
    
    
}
