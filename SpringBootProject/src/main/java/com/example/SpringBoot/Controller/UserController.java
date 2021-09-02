package com.example.SpringBoot.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.SpringBoot.Entities.User;
import com.example.SpringBoot.RepositaryService.UserRepositary;

@Controller
public class UserController {

	@Autowired
	public UserRepositary userRepositary;

	public UserController(UserRepositary userRepositary) {
		this.userRepositary = userRepositary;
	}

	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") int id, Model model) {
		try {
			userRepositary.deleteById(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "redirect:/dashboard";
	}

	@PostMapping("/profile/{id}")
	public String getProfile(@PathVariable("id") int id, Model model) {
		try {
			User user = userRepositary.findById(id).get();
			model.addAttribute("user", user);
			model.addAttribute("title", "User Profile");
			model.addAttribute("title1", "User Profile");
			return "profile";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.addAttribute("error", e.getMessage());
			return "profile";
		}
	}

	@GetMapping("/addemp")
	public String addEmployee(Model model, boolean isError) {
		model.addAttribute("title", "Add Employee");
		model.addAttribute("title1", "Add Employee Page");
		if (!isError)
			model.addAttribute("user", new User());
		return "addemployee";
	}

	@PostMapping("/addemp")
	public String processaddEmployee(@Valid @ModelAttribute User user, BindingResult bindingResult, Model model) {
		try {
//			System.out.println("user: " + user);
//			System.out.println("hasErrors: " + bindingResult.hasErrors());
//			System.out.println("bindingResult: " + bindingResult);
			if (bindingResult.hasErrors()) {
				return "addemployee";
			}
			if (user != null) {
				// System.out.println("user test");
				user.setName((user.getName()).toUpperCase());
				userRepositary.save(user);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.addAttribute("error", e.getMessage());
			return "addemployee";
		}
		return "redirect:/dashboard";
	}

	@RequestMapping("/updateprofile")
	public String updateprofile(@Valid @ModelAttribute User user, BindingResult bindingResult, Model model) {
		try {
			if (bindingResult.hasErrors()) {
				return "profile";
			}
			if (user != null) {
				userRepositary.save(user);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.addAttribute("error", e.getMessage());
			return "profile";
		}
		return "redirect:/dashboard";
	}

	@RequestMapping("/dashboard")
	public String dashboard(Model model, HttpServletRequest request) {
		try {
			String session = (String) request.getSession().getAttribute("username");
			if (session != null) {
				model.addAttribute("title", "Dashboard Page");
				model.addAttribute("title1", "Dashboard Page");
				List<User> list = userRepositary.findAll();
				model.addAttribute("userList", list);
				return "user-dashboard";
			}
			return "redirect:/userlogin";
		} catch (Exception e) {
			System.out.println("Exception : " + e);
		}
		return "login";
	}
}
