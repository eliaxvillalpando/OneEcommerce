package com.iudc.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class etiquetaController {
    
    
    @GetMapping("/etiqueta")
    private String vistaParaEtiquetaBasica(){
        
        return "front/etiquetas";
        
    }
    
    @GetMapping("/cotizador")
    private String verCotizador(){
        
        return "front/cotizador";
        
    }
    
    
    
}
