package com.example.demo.controller;

import com.example.demo.model.Contact;
import com.example.demo.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ContactController {
    @Autowired
    private ContactService contactService;

    @GetMapping("/")
    public String listContacts(Model model) {
        List<Contact> contacts = contactService.getAllContacts();
        model.addAttribute("contacts", contacts);
        model.addAttribute("contact", new Contact()); // 添加一个新的Contact对象用于表单
        model.addAttribute("editMode", false); // 添加标志属性，初始设置为false
        return "contacts";
    }

    @GetMapping("/edit/{id}")
    public String editContact(@PathVariable Long id, Model model) {
        Contact contact = contactService.getContactById(id);
        model.addAttribute("contact", contact); // 将选中的联系人信息添加到模型
        model.addAttribute("editMode", true); // 设置标志属性为true以显示编辑表单
        return "contacts";
    }

    @PostMapping("/add")
    public String addContact(@ModelAttribute Contact contact) {
        contactService.addContact(contact);
        return "redirect:/";
    }

    @PostMapping("/update/{id}")
    public String updateContact(@PathVariable Long id, @ModelAttribute Contact contact) {
        Contact existingContact = contactService.getContactById(id);
        if (existingContact != null) {
            existingContact.setName(contact.getName());
            existingContact.setAddress(contact.getAddress());
            existingContact.setPhone(contact.getPhone());
            contactService.addContact(existingContact); // 更新联系人信息
        }
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteContact(@PathVariable Long id) {
        contactService.deleteContact(id);
        return "redirect:/";
    }
}
