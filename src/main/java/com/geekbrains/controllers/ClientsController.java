package com.geekbrains.controllers;

import com.geekbrains.entities.Client;
import com.geekbrains.repositories.specifications.ClientSpecifications;
import com.geekbrains.services.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/client")
public class ClientsController {
    private ClientServiceImpl clientServiceImpl;
    private String templateFolder = "client/";

    @Autowired
    public void setClientServiceImpl(ClientServiceImpl _clientServiceImpl) {
        this.clientServiceImpl = _clientServiceImpl;
    }

    // Список клиентов
    // http://localhost:8189/app/сlient
    @GetMapping()
    public String getClientList(
            Model model,
            Pageable pageable,
            @RequestParam(name = "pages", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(name = "size", required = false, defaultValue = "5") int pageSize,
            @RequestParam(name = "name", required = false) String filterClientName
    ) {
        // Пагинация начинается с 0 элемента
        if (pageNumber > 0) {
            pageNumber --;
        }
        Specification<Client> spec = Specification.where(null);
        spec = spec.and(ClientSpecifications.getClientByName(filterClientName));

        Pageable firstPageWithFiveElements = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC,"id"));
        Page<Client> clientList = clientServiceImpl.showAllClient(spec, firstPageWithFiveElements);
        model.addAttribute("page", clientList);
        model.addAttribute("pages", pageNumber);
        model.addAttribute("size", pageSize);
        model.addAttribute("name", filterClientName);

        List<String> pageCountList = Arrays.asList("1", "5", "10", "30", "50");
        model.addAttribute("pageCountList", pageCountList);

        return templateFolder + "client-list";
    }

    // Форма редактирования клиента
    // http://localhost:8189/app/ware/edit-client
    @GetMapping("/edit-client")
    public String editWare(Model model, @RequestParam(name = "id", required = false, defaultValue = "0") Long id) {
        Optional<Client> editClient = Optional.of(new Client());

        // Редактирование существующего клиента
        if (id > 0) {
            editClient = clientServiceImpl.get(id);
        }
        model.addAttribute("page", editClient);
        return templateFolder + "edit-client";
    }

    // Сохранение клиента
    // http://localhost:8189/app/client/save-client
    @PostMapping("/save-client")
    public String saveClient(@ModelAttribute("client") Client client) {
        clientServiceImpl.save(client);
        return "redirect:/client/";
    }

    // Удаление клиента
    // http://localhost:8189/app/client/del-client/1
    @GetMapping("/del-client/{id}")
    public String delClient(@PathVariable(name = "id") Long id) {
        clientServiceImpl.delete(id);
        return "redirect:/client/";
    }
}