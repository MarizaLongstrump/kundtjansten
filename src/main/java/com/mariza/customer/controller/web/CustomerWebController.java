package com.mariza.customer.controller.web;

import com.mariza.customer.dto.CreateCustomerRequest;
import com.mariza.customer.dto.UpdateCustomerRequest;
import com.mariza.customer.service.CustomerServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customers")

public class CustomerWebController {

    private final CustomerServiceInterface customerService;

    @GetMapping
    public String listCustomers(Model model) {
        model.addAttribute("customers", customerService.getAllCustomers());
        return "customers/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("customer", new CreateCustomerRequest());
        return "customers/create";
    }

    @PostMapping("/create")
    public String createCustomer(@ModelAttribute("customer") CreateCustomerRequest request) {
        customerService.createCustomer(request);
        return "redirect:/customers";
    }

    @GetMapping("/{id}")
    public String viewCustomer(@PathVariable Long id, Model model) {
        model.addAttribute("customer", customerService.getCustomerById(id));
        return "customers/view";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("customer", customerService.getCustomerById(id));
        return "customers/edit";
    }

    @PostMapping("/{id}/edit")
    public String updateCustomer(@PathVariable Long id,
                                 @ModelAttribute("customer") UpdateCustomerRequest request) {
        customerService.updateCustomer(id, request);
        return "redirect:/customers";
    }

    @PostMapping("/{id}/delete")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/customers";
    }
}

