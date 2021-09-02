package com.example.SpringBoot.Controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.SpringBoot.Entities.User;
import com.example.SpringBoot.RepositaryService.UserRepositary;

@Controller
public class HomeController {

	@Autowired
	private UserRepositary userRepositary;

	@RequestMapping("/")
	public String getHome(Model model) {
		model.addAttribute("title", "Home- Sample Project");
		model.addAttribute("title1", "Home Page");
		return "home";
	}

	@RequestMapping("/userlogin")
	public String login(Model model, boolean isError) {
		model.addAttribute("title", "Login Page");
		model.addAttribute("title1", "Login Page");
		if (!isError)
			model.addAttribute("user", new User());
		return "login";
	}

	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("username");
		return "redirect:/userlogin";
	}

	@PostMapping("/userlogin")
	public String processlogin(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model,
			HttpSession session) {
		try {
			if (bindingResult.hasFieldErrors("email") || bindingResult.hasFieldErrors("password")) {
				return login(model, true);
			}
			String email = user.getEmail();
			String password = user.getPassword();
			User fetchedUser = userRepositary.getByEmailAndPassword(email, password);
			if (fetchedUser != null) {
				session.setAttribute("username", email);
				return "redirect:/dashboard";
			}
			model.addAttribute("login_error", "Invalid email or password");
			return login(model, false);
		} catch (Exception e) {
			model.addAttribute("login_error", e.getMessage());
			return login(model, false);
		}
	}

	@GetMapping("/register")
	public String register(Model model, boolean isError) {
		model.addAttribute("title", "Register Employee");
		model.addAttribute("title1", "Registration Page");
		if (!isError)
			model.addAttribute("user", new User());

		return "signup";
	}

	@PostMapping("/register")
	public String processregister(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
		try {
			if (bindingResult.hasErrors()) {
				return register(model, true);
			}
			if (user != null) {
				// System.out.println("user: " + user);
				user.setName((user.getName()).toUpperCase());
				userRepositary.save(user);
				model.addAttribute("success", "Register successfully!!");
			}
			return register(model, false);
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			return register(model, true);
		}
	}
}
