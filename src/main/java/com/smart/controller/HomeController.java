package com.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.smart.DTOs.LoginDTO;
import com.smart.entities.User;
import com.smart.helper.message;
import com.smart.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;


@Controller
public class HomeController {
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserRepository repository;
	
	
//	To get home page
	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("title", "Home-Pro Contacts");
		return "home.html";
	}
	
//	To get about page
	@GetMapping("/about")
	public String about(Model model) {
		model.addAttribute("title", "About-Pro Contacts");
		return "about.html";
	}
	
//	To get signup page
	@GetMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("title", "Register-Pro Contacts");
		model.addAttribute("user", new User());
		return "signup.html";
	}
	
		
//	actual registration of user	in signup page
	@PostMapping("/do_register")
	public String registeruser( @ModelAttribute("user") User user, 
	        @RequestParam(value = "agreement", defaultValue = "false") 
	        boolean agreement, Model model, BindingResult result1 ,HttpSession session) {
	    
	    try {
	        // Check if the user has agreed to terms and conditions
	        if (!agreement) {
	            System.out.println("Please agree for T&C");    
	            throw new Exception("Please agree for T&C");    
	        }
	        
	        if(result1.hasErrors()) {
	        	System.out.println("Error" + result1.toString());
	        	model.addAttribute("user", user);
	        	return "signup";
	        }
	        
	        // Set user role, enable the user, and set a default image URL
	        user.setEnabled(true);
	        user.setImageUrl("default.jpg");
	        user.addRole("USER");
	        user.setPassward(bCryptPasswordEncoder.encode(user.getPassward()));
	        
	        // Save the user data into the repository
	        this.repository.save(user);
	        
	        // Reset the "user" attribute to create a new User object for the form
	        model.addAttribute("user", new User());
	        
	        // Set a success message in the session and redirect to the signup page
	        session.setAttribute("message", new message("Woohoo !!! Successfully registered, Please login to Enjoy the feature of PRO_CONTACTS", "alert-success"));
	        return "redirect:/signup";
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        // Keep the user input in the form, set an error message in the session, and redirect to the signup page
	        model.addAttribute("user", user);
	        session.setAttribute("message", new message("Something went wrong:"+e.getMessage() , "alert-danger"));
	        return "redirect:/signup";
	    }  
	}
	
//	to get login page
	@GetMapping("/login")
	public String Customlogin(Model model) {
		model.addAttribute("title", "Log In -Pro Contacts");
		return "login.html";
	}
	
//	Actual login process
	@PostMapping("/log")
	public String login(@ModelAttribute LoginDTO loginDTO) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassward()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return "redirect:/";
	}
	
	
//	to get login-error page
//	@GetMapping("/login_error")
//	public String loginerror(Model model) {
//		model.addAttribute("title", "Error :( -Pro Contacts");
//		return "login_error.html";
//	}
}
