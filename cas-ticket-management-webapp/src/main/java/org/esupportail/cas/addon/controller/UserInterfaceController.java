package org.esupportail.cas.addon.controller;

import java.util.Arrays;
import java.util.List;

import org.esupportail.cas.addon.model.JsonTicket;
import org.esupportail.cas.addon.model.TicketOwner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/user")
public class UserInterfaceController {

	@Value("${server.api}")
	private String CAS_REST_API;

	@Value("${cas.ticket.expirationPolicy}")
	private long EXPIRATION_POLICY;

	@Value("${cas.ticket.rememberMeExpirationPolicy}")
	private long REMEMBER_ME_EXPIRATION_POLICY;

	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping(method = RequestMethod.GET)
	public String printIndex(ModelMap model, @RequestParam(value = "delete", required = false) boolean delete) {

		List<JsonTicket> resultList = Arrays.asList(
				this.restTemplate.getForObject(this.CAS_REST_API + "/{user}/", JsonTicket[].class, this.getCurrentUser()));

		model.addAttribute("delete", delete);
		model.addAttribute("expirationPolicyInSeconds", this.EXPIRATION_POLICY);
		model.addAttribute("rememberMeExpirationPolicyInSeconds", this.REMEMBER_ME_EXPIRATION_POLICY);
		model.addAttribute("userTickets", resultList);
		return "userIndex";
	}

	@RequestMapping(value = "/delete/{ticketId}", method = RequestMethod.GET)
	public String printDelete(ModelMap model, @PathVariable String ticketId) {

		String getOwnerUrl = this.CAS_REST_API + "/owner/{ticketId}/";
		TicketOwner result = this.restTemplate.getForObject(getOwnerUrl, TicketOwner.class, ticketId);

		if(this.getCurrentUser().equals(result.getOwner())) {

			String targetUrl = this.CAS_REST_API + "/ticket/{ticketId}/";
			this.restTemplate.delete(targetUrl, ticketId);
			return "redirect:/user?delete=true";
		}
		return "redirect:/user?delete=false";
	}

	private String getCurrentUser() {
		LdapUserDetailsImpl user = (LdapUserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String currentUser = user.getUsername();
		return currentUser;
	}

}