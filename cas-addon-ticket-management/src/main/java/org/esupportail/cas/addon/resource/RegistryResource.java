package org.esupportail.cas.addon.service;

import java.util.List;

import org.esupportail.cas.addon.utils.TicketRegistryUtils;
import org.esupportail.cas.addon.model.JsonTicket;
import org.esupportail.cas.addon.model.TicketOwner;
import org.jasig.cas.CentralAuthenticationService;
import org.jasig.cas.ticket.Ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/registry")
public class RegistryResource {

	@Autowired
	private CentralAuthenticationService cas;

	@Autowired
	private TicketRegistryUtils utils;

	/**
	 * Rest method to get all ticket for a user.
	 *
	 * @return Ticket list for a given user 
	 */
	@RequestMapping(value = "/{user}/", method = RequestMethod.GET)
	public @ResponseBody List<JsonTicket> getListForUser(@PathVariable String user) {

		return this.utils.listTicketToJson(user);
	}

	/**
	 * Rest Method to delete a ticket from the ticket registry
	 * :.+ in the RequestMapping annotation prevents Spring
	 * from removing the suffig pattern of our tickets
	 *
	 * @return empty JSON. CAS server always return `true` a ticket is deleted
	 */
	@RequestMapping(value="/ticket/{ticketId:.+}/", method=RequestMethod.DELETE)
	public @ResponseBody String deleteTicket(@PathVariable String ticketId) {

		this.cas.destroyTicketGrantingTicket(ticketId);
		return "{}";
	}

	/**
	 * Rest method to get the ticket's Authentication Principal id
	 *
	 * @return Ticket Owner in JSON format
	 */
	@RequestMapping(value="/owner/{ticketId:.+}/", method = RequestMethod.GET)
	public @ResponseBody TicketOwner getTicketOwner(@PathVariable String ticketId) {

		String userId = this.utils.ticketBelongTo(ticketId);
		return new TicketOwner(userId);
	}

	/**
	 * Rest method that deletes all the valid tickets for a specific user
	 *
	 * @return empty JSON, see deleteTicket method.
	 */
	@RequestMapping(value="/userTickets/{user}/", method=RequestMethod.DELETE)
	public @ResponseBody String deleteAllForUser(@PathVariable String user) {

		List<Ticket> userTickets = this.utils.listTicketForUser(user);
		for(Ticket ticket : userTickets) {
			this.cas.destroyTicketGrantingTicket(ticket.getId());
		}
		return "{}";
	}

}