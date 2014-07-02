package org.esupportail.cas.addon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class WebController {

	@RequestMapping(method = RequestMethod.GET)
	public String printIndex(ModelMap model) {
		return "index";
	}

}
