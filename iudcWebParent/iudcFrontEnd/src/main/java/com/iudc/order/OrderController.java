package com.iudc.order;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import com.iudc.ControllerHelper;
import com.iudc.common.entity.Customer;
import com.iudc.common.entity.order.Order;
import com.iudc.common.entity.order.OrderDetail;
import com.iudc.common.entity.product.Product;
import com.iudc.common.entity.product.ProductoCotizador;
import com.iudc.review.ReviewService;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ControllerHelper controllerHelper;
    @Autowired
    private ReviewService reviewService;

    @GetMapping("/orders")
    public String listFirstPage(Model model, HttpServletRequest request) {
        return listOrdersByPage(model, request, 1, "orderTime", "desc", null);
    }

    @GetMapping("/orders/page/{pageNum}")
    public String listOrdersByPage(Model model, HttpServletRequest request,
            @PathVariable(name = "pageNum") int pageNum,
            String sortField, String sortDir, String keyword
    ) {
        Customer customer = controllerHelper.getAuthenticatedCustomer(request);

        Page<Order> page = orderService.listForCustomerByPage(customer, pageNum, sortField, sortDir, keyword);
        List<Order> listOrders = page.getContent();

        //Map para relacionar Orden-OrdenDetails-ProductoCotizador y mostrarlo en html
        Map<Long, String> medidasPorOrden = new HashMap<>();
        for (Order order : listOrders) {
            Set<OrderDetail> orderDetailSet = order.getOrderDetails();
            if (orderDetailSet != null && !orderDetailSet.isEmpty()) {
                OrderDetail orderDetail = orderDetailSet.iterator().next();
                ProductoCotizador productoCotizador = orderDetail.getProductoCotizador();
                if (productoCotizador != null) {
                    String medidas = productoCotizador.getMedidas();
                    String superficieAplicacion = productoCotizador.getSuperficieAplicacion();
                    String forma = productoCotizador.getForma();
                    String papel = productoCotizador.getPapel();
                    String impresion = productoCotizador.getImpresion();
                    medidasPorOrden.put((long) order.getId(), "Medidas : " + medidas
                            + "</br>" + "Superficie de aplicación: "
                            + superficieAplicacion + "</br>" + "Forma: " + forma
                            + "</br>" + "Papel: " + papel
                            + "</br>" + "Impresión: " + impresion);

                }
            }
        }

        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("listOrders", listOrders);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("keyword", keyword);
        model.addAttribute("medidasPorOrden", medidasPorOrden);

        model.addAttribute("moduleURL", "/orders");
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        long startCount = (pageNum - 1) * OrderService.ORDERS_PER_PAGE + 1;
        model.addAttribute("startCount", startCount);

        long endCount = startCount + OrderService.ORDERS_PER_PAGE - 1;
        if (endCount > page.getTotalElements()) {
            endCount = page.getTotalElements();
        }

        model.addAttribute("endCount", endCount);

        return "orders/orders_customer";
    }

    @GetMapping("/orders/detail/{id}")
    public String viewOrderDetails(Model model,
            @PathVariable(name = "id") Integer id, HttpServletRequest request) {
        Customer customer = controllerHelper.getAuthenticatedCustomer(request);
        Order order = orderService.getOrder(id, customer);

        //Map<Long, String> medidasPorOrden = (Map<Long, String>) model.getAttribute("medidasPorOrden");
        Set<OrderDetail> orderDetails = order.getOrderDetails();

        if (!order.isTipoCotizador()) {

            setProductReviewableStatus(customer, order);
            model.addAttribute("order", order);
            return "orders/order_details_modal";

        } else {

            for (OrderDetail orderDetail : orderDetails) {
                ProductoCotizador productoCotizador = orderDetail.getProductoCotizador();

                // Acceder a los datos de la tabla ProductoCotizador
                String superficieAplicacion = productoCotizador.getSuperficieAplicacion();
                String impresion = productoCotizador.getImpresion();
                String forma = productoCotizador.getForma();
                String medidas = productoCotizador.getMedidas();
                String papel = productoCotizador.getPapel();
                String color = productoCotizador.getColor();
                String acabado = productoCotizador.getAcabado();
                String salida = productoCotizador.getSalida();
                String presentacion = productoCotizador.getPresentacion();
                Double precioMillar = productoCotizador.getPrecioMillar();
                Double minimoFabricacion = productoCotizador.getMinimoFabricacion();
                Double cantidadPedido = productoCotizador.getCantidadPedido();
                
                String codigoCotizacion = productoCotizador.getCodigoCotizacion();

                // Agregar los datos al modelo para mostrarlos en la página HTML
                model.addAttribute("superficieAplicacion", superficieAplicacion);
                model.addAttribute("impresion", impresion);
                model.addAttribute("forma", forma);
                model.addAttribute("medidas", medidas);
                model.addAttribute("papel", papel);
                model.addAttribute("color", color);
                model.addAttribute("acabado", acabado);
                model.addAttribute("salida", salida);
                model.addAttribute("presentacion", presentacion);
                model.addAttribute("precioMillar", precioMillar);
                model.addAttribute("minimoFabricacion", minimoFabricacion);
                model.addAttribute("cantidadPedido", cantidadPedido);
                model.addAttribute("codigoCotizacion", codigoCotizacion);
            }
            //model.addAttribute("medidasPorOrden", medidasPorOrden);
            //setProductReviewableStatus(customer, order);
            model.addAttribute("order", order);
            return "orders/order_details_modal_cotizador";
        }

    }

    private void setProductReviewableStatus(Customer customer, Order order) {
        Iterator<OrderDetail> iterator = order.getOrderDetails().iterator();

        while (iterator.hasNext()) {
            OrderDetail orderDetail = iterator.next();
            Product product = orderDetail.getProduct();
            Integer productId = product.getId();

            boolean didCustomerReviewProduct = reviewService.didCustomerReviewProduct(customer, productId);
            product.setReviewedByCustomer(didCustomerReviewProduct);

            if (!didCustomerReviewProduct) {
                boolean canCustomerReviewProduct = reviewService.canCustomerReviewProduct(customer, productId);
                product.setCustomerCanReview(canCustomerReviewProduct);
            }

        }
    }
}
