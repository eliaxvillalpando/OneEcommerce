package com.iudc.common.entity.product;

import com.iudc.common.entity.IdBasedEntity;
import com.iudc.common.entity.order.OrderStatus;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PostPersist;
import javax.persistence.Table;

@Entity
@Table(name = "prodCotizador")
public class ProductoCotizador extends IdBasedEntity{
    
    private Date orderTime;
    //nullable = false
    @Column(length = 25)
    private String superficieAplicacion;
    
    @Column(length = 65)
    private String impresion;
    
    @Column(length = 20)
    private String forma;
    
    @Column(length = 60)
    private String medidas;
    
    @Column(length = 120)
    private String papel;
    
    @Column(length = 25)
    private String color;
    
    @Column(length = 35)
    private String acabado;
    
    @Column(length = 35)
    private String salida;
    
    @Column(length = 65)
    private String presentacion;
    
    @Column()
    private Double precioMillar;
    
    @Column()
    private Double minimoFabricacion;
    
    @Column()
    private Double cantidadPedido;
    
    @Column()
    private Integer customerId;
    
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    
    @Column(name = "codigoCotizacion")
    private String codigoCotizacion;

    @PostPersist
    public void setCodigoCotizacion() {
        codigoCotizacion = "Cotizacion-" + String.format("%d", id);
    }

    public String getCodigoCotizacion() {
        return codigoCotizacion;
    }
    

    public ProductoCotizador() {
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getSuperficieAplicacion() {
        return superficieAplicacion;
    }

    public void setSuperficieAplicacion(String superficieAplicacion) {
        this.superficieAplicacion = superficieAplicacion;
    }

    public String getImpresion() {
        return impresion;
    }

    public void setImpresion(String impresion) {
        this.impresion = impresion;
    }

    public String getForma() {
        return forma;
    }

    public void setForma(String forma) {
        this.forma = forma;
    }

    public String getMedidas() {
        return medidas;
    }

    public void setMedidas(String medidas) {
        this.medidas = medidas;
    }

    public String getPapel() {
        return papel;
    }

    public void setPapel(String papel) {
        this.papel = papel;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getAcabado() {
        return acabado;
    }

    public void setAcabado(String acabado) {
        this.acabado = acabado;
    }

    public String getSalida() {
        return salida;
    }

    public void setSalida(String salida) {
        this.salida = salida;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public Double getPrecioMillar() {
        return precioMillar;
    }

    public void setPrecioMillar(Double precioMillar) {
        this.precioMillar = precioMillar;
    }

    public Double getMinimoFabricacion() {
        return minimoFabricacion;
    }

    public void setMinimoFabricacion(Double minimoFabricacion) {
        this.minimoFabricacion = minimoFabricacion;
    }

    public Double getCantidadPedido() {
        return cantidadPedido;
    }

    public void setCantidadPedido(Double cantidadPedido) {
        this.cantidadPedido = cantidadPedido;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    

    
    
    
    
}