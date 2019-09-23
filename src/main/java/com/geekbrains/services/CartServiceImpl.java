package com.geekbrains.services;

import com.geekbrains.entities.Cart;
import com.geekbrains.entities.Product;
import com.geekbrains.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl {
    private CartRepository cartRepository;

    @Autowired
    public void setWareRepository(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Transactional(readOnly = true)
    public Page<Cart> getAll(Pageable pageable) {
        return (Page<Cart>) cartRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Cart> getAll() {
        return (List<Cart>) cartRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Cart get(Long id) {
        return cartRepository.findById(id).get();
    }

    @Transactional
    public void save(Cart cart) {
        cartRepository.save(cart);
    }

    @Transactional
    public void addToCart(Product product) {
        Long productAmount = null;
        Long productId;
        Long clientId = 5L;
        Long cartId = null;

        productId = product.getId();
        Cart cart = cartRepository.getProductByUser(productId, clientId);
        if (cart != null) {
            productAmount = cart.getProductAmount();
            cartId = cart.getCartId();
        } else {
            cart = new Cart();
        }

        if (productAmount == null) {
            productAmount = 0L;
        }

        productAmount ++;
        cart.setCartId(cartId);
        cart.setProductId(productId);
        cart.setClientId(5L);
        cart.setProductCost(product.getPrice());
        cart.setProductAmount(productAmount);
        cartRepository.save(cart);
    }

    @Transactional
    public void delete(Long idProduct, Long idClient) {
        //cartRepository.deleteById(id);
    }

    public void delProductByUser(Long idProduct, Long idClient) {
        cartRepository.delProductByUser(idProduct, idClient);
    }
}