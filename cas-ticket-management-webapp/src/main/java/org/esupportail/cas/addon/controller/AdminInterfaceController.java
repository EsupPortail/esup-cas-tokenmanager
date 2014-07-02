package org.esupportail.cas.addon.controller;

import org.esupportail.cas.addon.model.TicketOwner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/admin")
public class AdminInterfaceController {

	@Value("${server.api}")
	private String CAS_REST_API;

	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping(method = RequestMethod.GET)
	public String printIndex(ModelMap model, @RequestParam(required = false) boolean delete) {

		model.addAttribute("command", new TicketOwner());
		model.addAttribute("delete", delete);
		return "adminIndex";
	}

	@RequestMapping(value="/deleteAll", method = RequestMethod.POST)
	public String handleDeleteForm(@ModelAttribute("TicketOwner") TicketOwner ticketOwner, BindingResult result) {

		String userId = ticketOwner.getOwner();

		String targetUrl = this.CAS_REST_API + "/userTickets/{userId}/";
		this.restTemplate.delete(targetUrl, userId);

		return "redirect:/admin?delete=true";
	}
}
