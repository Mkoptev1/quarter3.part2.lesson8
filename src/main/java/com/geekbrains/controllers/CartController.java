package com.geekbrains.controllers;

import com.geekbrains.entities.Cart;
import com.geekbrains.entities.Product;
import com.geekbrains.services.CartServiceImpl;
import com.geekbrains.services.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
    private CartServiceImpl cartServiceImpl;
    private ProductServiceImpl productServiceImpl;

    @Autowired
    public void setCartService(CartServiceImpl cartServiceImpl) {
        this.cartServiceImpl = cartServiceImpl;
    }

    @Autowired
    public void setProductServiceImpl(ProductServiceImpl productServiceImpl) {
        this.productServiceImpl = productServiceImpl;
    }

    @GetMapping()
    public String getCartList(Model model) {
        List<Cart> cartList = cartServiceImpl.getAll();
        model.addAttribute("Cart", cartList);
        return "cart";
    }

    // Добавление товара в корзину
    @GetMapping("/add")
    public void addProductToCart(@RequestParam("id") Long id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Product product = productServiceImpl.get(id);
        cartServiceImpl.addToCart(product);
        response.sendRedirect(request.getHeader("referer"));
    }

    // Удаление товара из корзины
    @GetMapping("/del")
    public String delWare(@RequestParam("idp") Long idProduct, @RequestParam("idc") Long idClient) {
        cartServiceImpl.delProductByUser(idProduct, idClient);
        return "redirect:/cart/";
    }
}
