package org.esupportail.cas.addon.controller;

import java.util.Arrays;

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

	@Value("${ip.geolocation}")
	private boolean ACTIVATE_IP_GEOLOCATION;

	@Value("${ticket.nbToDisplay.perPage}")
	private int nbToDisplay;

	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping(method = RequestMethod.GET)
	public String printIndex(ModelMap model, @RequestParam(value = "delete", required = false) boolean delete,
			@RequestParam(value = "page", required = false) Integer page) {

		JsonTicket[] listTicket = this.restTemplate.getForObject(this.CAS_REST_API + "/{user}/", JsonTicket[].class, this.getCurrentUser());

		int pageNumber = (int) Math.floor( listTicket.length / this.nbToDisplay );
		if(page == null || page == 0) {
			page = 0;
		} else {
			page--;
		}

		int startPoint = page * this.nbToDisplay;
		int endPoint = startPoint + this.nbToDisplay;

		// Just some calculation to make sure we won't get ArrayOutOfBoundException
		if(startPoint > listTicket.length) {
			page = pageNumber--;
			startPoint = page * this.nbToDisplay;
		}
		endPoint = endPoint < listTicket.length ? endPoint : listTicket.length;

		JsonTicket[] returnedTicket = Arrays.copyOfRange(listTicket, startPoint, endPoint);

		model.addAttribute("delete", delete);
		model.addAttribute("pageNumber", pageNumber);
		model.addAttribute("currentPage", page);
		model.addAttribute("expirationPolicyInSeconds", this.EXPIRATION_POLICY);
		model.addAttribute("rememberMeExpirationPolicyInSeconds", this.REMEMBER_ME_EXPIRATION_POLICY);
		model.addAttribute("activateIpGeolocation", this.ACTIVATE_IP_GEOLOCATION);
		model.addAttribute("userTickets", returnedTicket);
		model.addAttribute("pageTitle", "user.title");
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