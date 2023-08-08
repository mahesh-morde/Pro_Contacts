package com.smart.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smart.entities.Contacts;
import com.smart.entities.User;
import com.smart.repositories.UserRepository;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository repository;
	
//	method to add common data to response
	@ModelAttribute
	private void addcommondata(Model model, Principal principal) {
//		String userName = principal.getName();		
//		Optional<User> user = repository.findByEmail(userName);	
//		model.addAttribute("user", user);
	}
	
//	dashboard/home/for user
	@GetMapping("/index")
	public String reguser(Model model) {		
		model.addAttribute("title", "User Dashboard -Pro Contacts");
		return "normal/user_dashboard.html";
	}
	
//	open addcontact form handler
	@GetMapping("/add-contacts")
	public String addcontact(Model model) {		
		model.addAttribute("title", "Add Contact -Pro Contacts");
		model.addAttribute("contact", new Contacts());
		return "normal/add_contact_form.html";
	}
}
