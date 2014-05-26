package org.esupportail.cas.addon.controllers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.jasig.cas.CentralAuthenticationService;
import org.jasig.cas.authentication.Authentication;
import org.jasig.cas.ticket.Ticket;
import org.jasig.cas.ticket.TicketGrantingTicket;
import org.jasig.cas.ticket.registry.TicketRegistry;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class UserRevocationController extends AbstractController {

	@NotNull
	private CentralAuthenticationService centralAuthenticationService;
	private final TicketRegistry ticketRegistry;
	private int expirationPolicyInSeconds;
	private int rememberMeExpirationPolicyInSeconds;

	public UserRevocationController(final TicketRegistry ticketRegistry) {
		this.ticketRegistry = ticketRegistry;
	}

	@Override
	protected ModelAndView handleRequestInternal(final HttpServletRequest request, final HttpServletResponse response) throws Exception {

		String authenticatedUser = "admin";

		System.out.println(request.getRemoteUser());

		ModelMap model = new ModelMap();

		/**
		 * Not secured at all
		 * Just for prototyping purpose
		 */
		String ticketParam = request.getParameter("ticket");
		if(ticketParam != null) {
			this.centralAuthenticationService.destroyTicketGrantingTicket(ticketParam);
			model.put("destroyTicketMessage", "Ticket : " + ticketParam + " successfully destroyed from the ticket registry");
		}

		Map<Authentication, Ticket> userTickets = this.listTicketForUser(authenticatedUser);

		model.put("expirationPolicyInSeconds", this.expirationPolicyInSeconds);
		model.put("rememberMeExpirationPolicyInSeconds", this.rememberMeExpirationPolicyInSeconds);
		model.put("userTickets", userTickets);
		model.put("authenticatedUser", authenticatedUser);

		return new ModelAndView("revocationView", model);
	}

	private Map<Authentication, Ticket> listTicketForUser(String userId) {

		Map<Authentication, Ticket> userTickets = new HashMap<Authentication, Ticket>();

		try {

			// Get all ST and TGT from the ticket registry
			final Collection<Ticket> tickets = this.ticketRegistry.getTickets();

			for (final Ticket ticket : tickets) {

				//                Check if the ticket is a TGT and if it is not expired
				if (ticket instanceof TicketGrantingTicket && !ticket.isExpired()) {

					// Cast Ticket to TicketGrantingTicket to access specific tgt methods
					TicketGrantingTicket tgt = (TicketGrantingTicket) ticket;
					String ticketOwner = tgt.getAuthentication().getPrincipal().getId();

					if(ticketOwner.equalsIgnoreCase(userId)) {
						userTickets.put(tgt.getAuthentication(), ticket);
					}

				}
			}

		} catch (final UnsupportedOperationException e) {
			e.printStackTrace();
		}

		return userTickets;
	}

	public void setCentralAuthenticationService(
			final CentralAuthenticationService centralAuthenticationService) {
		this.centralAuthenticationService = centralAuthenticationService;
	}

	public void setExpirationPolicyInSeconds(final int expirationPolicyInSeconds) {
		this.expirationPolicyInSeconds = expirationPolicyInSeconds;
	}

	public void setRememberMeExpirationPolicyInSeconds(final int rememberMeExpirationPolicyInSeconds) {
		this.rememberMeExpirationPolicyInSeconds = rememberMeExpirationPolicyInSeconds;
	}

}