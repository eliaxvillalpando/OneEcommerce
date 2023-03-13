package com.iudc.cotizador;

import java.util.*;
import com.iudc.ControllerHelper;
import com.iudc.address.AddressService;
import com.iudc.checkout.CheckoutController;
import com.iudc.checkout.CheckoutInfo;
import com.iudc.checkout.CheckoutService;
import com.iudc.checkout.paypal.PayPalService;
import com.iudc.common.entity.Address;
import com.iudc.common.entity.CartItem;
import com.iudc.common.entity.Customer;
import com.iudc.common.entity.ShippingRate;
import com.iudc.common.entity.product.ProductoCotizador;
import com.iudc.order.OrderService;
import com.iudc.setting.PaymentSettingBag;
import com.iudc.setting.SettingService;
import com.iudc.shipping.ShippingRateService;
import com.iudc.shoppingcart.ShoppingCartRestController;
import com.iudc.shoppingcart.ShoppingCartService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PedidoDeCotizacionController {

    @Autowired
    private SettingService settingService;
    @Autowired
    private CheckoutService checkoutService;
    @Autowired
    private ControllerHelper controllerHelper;
    @Autowired
    private AddressService addressService;
    @Autowired
    private ShippingRateService shipService;
    @Autowired
    private ShoppingCartService cartService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private PayPalService paypalService;
    @Autowired
    private CotizadorService cotizadorService;
    @Autowired
    private CheckoutController checkoutController;
    @Autowired
    private ShoppingCartRestController shoppingCartController;

    
    @GetMapping("/pedido-cotizacion")
    private String pedidoCotizacionView(Model model, HttpServletRequest request,HttpSession session) {

        ProductoCotizador productoCotizacion = (ProductoCotizador) session.getAttribute("productoCotizacion");
        Customer customer = controllerHelper.getAuthenticatedCustomer(request);

        Address defaultAddress = addressService.getDefaultAddress(customer);
        ShippingRate shippingRate = null;

        if (defaultAddress != null) {
            model.addAttribute("shippingAddress", defaultAddress.toString());
            shippingRate = shipService.getShippingRateForAddress(defaultAddress);
        } else {
            model.addAttribute("shippingAddress", customer.toString());
            shippingRate = shipService.getShippingRateForCustomer(customer);
        }

        if (shippingRate == null) {
            return "redirect:/cart";
        }
        List<CartItem> cartItems = cartService.listCartItems(customer);
        //CheckoutInfo checkoutInfo = checkoutService.prepareCheckout(cartItems, shippingRate);

        
        
        String currencyCode = settingService.getCurrencyCode();
        PaymentSettingBag paymentSettings = settingService.getPaymentSettings();
        String paypalClientId = paymentSettings.getClientID();
        
        String aplicacion = productoCotizacion.getSuperficieAplicacion();
        String medidas = productoCotizacion.getMedidas();
        String papel = productoCotizacion.getPapel();
        String presentacion = productoCotizacion.getPresentacion();
        String impresion = productoCotizacion.getImpresion();
        String forma = productoCotizacion.getForma();
        String color = productoCotizacion.getColor();
        String superficie = productoCotizacion.getSuperficieAplicacion();
        String salidaEtiqueta = productoCotizacion.getSalida();
        
        
        double precioMillarDbl = productoCotizacion.getPrecioMillar();
        double minimoFabricacionDbl = productoCotizacion.getMinimoFabricacion();
        double cantidadPedidoDbl = productoCotizacion.getCantidadPedido();
        double costoEnvio = 350.5;
        double totalPedido = productoCotizacion.getCantidadPedido() * productoCotizacion.getPrecioMillar();
        //double totalConEnvio = totalPedido + checkoutInfo.getShippingCostTotal();
        double totalConEnvio = totalPedido + costoEnvio;
        CheckoutInfo checkoutInfo = new CheckoutInfo();
        checkoutInfo.setPaymentTotal((float) totalConEnvio);
        
        
        
        model.addAttribute("aplicacion", aplicacion);
        model.addAttribute("paypalClientId", paypalClientId);
        model.addAttribute("currencyCode", currencyCode);
        model.addAttribute("customer", customer);
        model.addAttribute("checkoutInfo", checkoutInfo);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("cantidadPedidoDbl", cantidadPedidoDbl);
        model.addAttribute("minimoFabricacionDbl", minimoFabricacionDbl);
        model.addAttribute("precioMillarDbl", precioMillarDbl);
        model.addAttribute("costoEnvio", costoEnvio);
        model.addAttribute("totalPedido", totalPedido);
        model.addAttribute("totalConEnvio", totalConEnvio);
        model.addAttribute("medidas", medidas);
        model.addAttribute("papel", papel);
        model.addAttribute("presentacion", presentacion);
        model.addAttribute("impresion", impresion);
        model.addAttribute("forma", forma);
        model.addAttribute("color", color);
        model.addAttribute("superficie", superficie);
        model.addAttribute("salidaEtiqueta", salidaEtiqueta);


        //return "cart/shopping_cart";
        return "front/pedido-cotizacion";

    }

    @PostMapping("/crear-pedido-cotizacion")
    public String myEndpoint(@RequestBody List<String> datos, Model model, HttpServletRequest request, RedirectAttributes ra, HttpSession session) {
        
        ProductoCotizador productoCotizacion = new ProductoCotizador();
        session.setAttribute("productoCotizacion", productoCotizacion);
        for (String element : datos) {
            System.out.println(element);
        }
        System.out.println(datos.size());

        List<String> datosFormato = new ArrayList<>();

        for (String s : datos) {
            String replaced = s.replaceAll("\\\\", " ");
            datosFormato.add(replaced);
        }

        //String cantidadPedido = request.getParameter("cantidadPedido");
        String superficieAplicacion = datosFormato.get(0);
        String impresion = datosFormato.get(1);
        String forma = datosFormato.get(2);
        String medidas = datosFormato.get(3);
        String papel = datosFormato.get(4);
        String color = datosFormato.get(5);
        String acabado = datosFormato.get(6);
        String salida = datosFormato.get(7);
        String presentacion = datosFormato.get(8);

        String precioMillar = datosFormato.get(9);
        String minimoFabricacion = datosFormato.get(10);
        String cantidadPedido = datosFormato.get(datosFormato.size() - 1);
        double precioMillarDbl = Double.parseDouble(precioMillar);
        double minimoFabricacionDbl = Double.parseDouble(minimoFabricacion);
        double cantidadPedidoDbl = Double.parseDouble(cantidadPedido);

        Customer customer = controllerHelper.getAuthenticatedCustomer(request);
        int customerIdCotizacion = customer.getId();
        Calendar calendar = Calendar.getInstance();
        Date orderTime = calendar.getTime();

        
        productoCotizacion.setCustomerId(customerIdCotizacion);
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
        productoCotizacion.setPrecioMillar(precioMillarDbl);
        productoCotizacion.setMinimoFabricacion(minimoFabricacionDbl);
        productoCotizacion.setCantidadPedido(cantidadPedidoDbl);
        cotizadorService.savePedidoCotizacion(productoCotizacion);
        
        
        int codigoCotizacionId = productoCotizacion.getId();
        int quantity = productoCotizacion.getCantidadPedido().intValue();
        //shoppingCartController.addCotizacionPedidoToCart(codigoCotizacionId, quantity, customer);
        //datosPaypal(model, request);
        
        //return "cart/shopping_cart";
        
        return "front/pedido-cotizacion";
     
    }
    

}
