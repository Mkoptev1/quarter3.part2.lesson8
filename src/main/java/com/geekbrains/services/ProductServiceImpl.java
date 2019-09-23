package com.geekbrains.services;

import com.geekbrains.entities.Product;
import com.geekbrains.repositories.WareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl {
    private WareRepository wareRepository;

    @Autowired
    public void setWareRepository(WareRepository _wareRepository) {
        this.wareRepository = _wareRepository;
    }

    @Transactional(readOnly = true)
    public Page<Product> getAll(Pageable pageable) {
        return (Page<Product>) wareRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Product> getAll() {
        return (List<Product>) wareRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Product get(Long id) {
        return wareRepository.findById(id).get();
    }

    @Transactional
    public void save(Product ware) {
        wareRepository.save(ware);
    }

    @Transactional
    public Product saveAddRest(Product ware) {
        return wareRepository.save(ware);
    }

    @Transactional
    public void delete(Long id) {
        wareRepository.deleteById(id);
    }

    @Transactional
    public Page<Product> showAllWare(Specification<Product> specification, Pageable pageable) {
        return (Page<Product>) wareRepository.findAll(specification, pageable);
    }

    public Product saveEditRest(Product editWare) {
        return wareRepository.save(editWare);
    }
}