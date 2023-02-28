/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iudc.cotizador;

import com.iudc.ControllerHelper;
import com.iudc.common.entity.Customer;
import com.iudc.common.entity.product.ProductoCotizador;
import com.iudc.customer.CustomerRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CotizadorService {
    
    
    @Autowired CotizadorRepository cotizadorRepo;
    @Autowired CustomerRepository customerRepo;
    @Autowired ControllerHelper controllerHelper;
    
    
    
    
    public void savePedidoCotizacion(ProductoCotizador productoCotizador){

        cotizadorRepo.save(productoCotizador);
    
    }
    
    






}



