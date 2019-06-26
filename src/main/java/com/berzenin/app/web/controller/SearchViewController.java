package com.berzenin.app.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.berzenin.app.model.Stell;
import com.berzenin.app.parsers.MainParser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class SearchViewController {
	
	@Autowired
	private MainParser parser;

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String add(Model model) {
		model.addAttribute("stell", new Stell());
		return "search";
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String processAdd(@Valid Stell person, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "search";
		}
		person.setId(0);
		person.setLink("https://www.alumeco.com/aluminium/g/359");
		person.setBrand("");
		person.setAlloy("");
		person.setTypeProduction("");
		person.setAmount(100);
		person.setSize("");
		person.setStandart("");
		person.setPerformative("");
		
		parser.getResultFromWebSite();
		
		System.out.println(person.getBrand() + " link" + person.getLink());

		return "search";
	}

//	@RequestMapping(method = RequestMethod.GET)
//	public String getSearch () {
//		return "search";
//	}
//
//    @RequestMapping(value = "employee")
//    public ModelAndView showForm() {
//    	System.out.println("New entity");
//        return new ModelAndView("employeeHome", "employee", new Employee());
//    }
// 
//    @RequestMapping(value = "/addEmployee", method = RequestMethod.POST)
//    public String submit(@Valid @ModelAttribute("employee")Employee employee, 
//      BindingResult result, ModelMap model) {
////        if (result.hasErrors()) {
////            return "error";
////        }
////        model.addAttribute("name", employee.getName());
////        model.addAttribute("contactNumber", employee.getContactNumber());
////        model.addAttribute("id", employee.getId());
//        return "search";
//    }

}
