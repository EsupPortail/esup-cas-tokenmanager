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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;

/**
 * The Class UserRevocationController.
 */
public class UserRevocationController extends AbstractController {

	/** The central authentication service. */
	@NotNull
	private CentralAuthenticationService centralAuthenticationService;

	/** The ticket registry. */
	private final TicketRegistry ticketRegistry;

	/** The Ticket Granting Ticket expiration policy in seconds. */
	private int expirationPolicyInSeconds;

	/** The Ticket Granting Ticket remember me expiration policy in seconds. */
	private int rememberMeExpirationPolicyInSeconds;

	/**
	 * Instantiates a new user revocation controller.
	 *
	 * @param ticketRegistry the ticket registry
	 */
	public UserRevocationController(final TicketRegistry ticketRegistry) {
		this.ticketRegistry = ticketRegistry;
	}

	/**
	 * Request handler
	 * This will list all the current session for the authenticated user
	 * The user can also revoke a specific session
	 */
	@Override
	protected ModelAndView handleRequestInternal(final HttpServletRequest request, final HttpServletResponse response) throws Exception {

		LdapUserDetailsImpl user = (LdapUserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                String currentUser = user.getUsername();

		ModelMap model = new ModelMap();

		String ticketParam = request.getParameter("ticket");
		if(ticketParam != null && ticketBelongToCurrentUser(ticketParam, currentUser)) {
			this.centralAuthenticationService.destroyTicketGrantingTicket(ticketParam);
			model.put("ticketDestroyed", true);
		}

		Map<Authentication, Ticket> userTickets = this.listTicketForUser(currentUser);

		model.put("expirationPolicyInSeconds", this.expirationPolicyInSeconds);
		model.put("rememberMeExpirationPolicyInSeconds", this.rememberMeExpirationPolicyInSeconds);
		model.put("userTickets", userTickets);
		model.put("authenticatedUser", currentUser);

		return new ModelAndView("revocationView", model);
	}

	/**
	 * List active Ticket Granting Ticket for the authenticated user.
	 *
	 * @param userId the user id
	 * @return the map
	 */
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

	/**
	 * Check if the authenticated correspond to the Ticket's principal ID
	 *
	 * @param ticketId
	 * @param currentUser
	 * @return true, if the ticket belongs to the current user
	 */
	private boolean ticketBelongToCurrentUser(String ticketId, String currentUser) {
		// Get all ST and TGT from the ticket registry
		final Collection<Ticket> tickets = this.ticketRegistry.getTickets();

		for (final Ticket ticket : tickets) {

			//                Check if the ticket is a TGT and if it is not expired
			if (ticket instanceof TicketGrantingTicket && ticket.getId().equalsIgnoreCase(ticketId)) {
				TicketGrantingTicket tgt = (TicketGrantingTicket) ticket;
				String ticketOwner = tgt.getAuthentication().getPrincipal().getId();

				if(ticketOwner.equalsIgnoreCase(currentUser)) {
					return true;
				}

			}
		}

		return false;
	}

	/**
	 * Sets the central authentication service.
	 *
	 * @param centralAuthenticationService the new central authentication service
	 */
	public void setCentralAuthenticationService(
			final CentralAuthenticationService centralAuthenticationService) {
		this.centralAuthenticationService = centralAuthenticationService;
	}

	/**
	 * Sets the expiration policy in seconds.
	 *
	 * @param expirationPolicyInSeconds the new expiration policy in seconds
	 */
	public void setExpirationPolicyInSeconds(final int expirationPolicyInSeconds) {
		this.expirationPolicyInSeconds = expirationPolicyInSeconds;
	}

	/**
	 * Sets the remember me expiration policy in seconds.
	 *
	 * @param rememberMeExpirationPolicyInSeconds the new remember me expiration policy in seconds
	 */
	public void setRememberMeExpirationPolicyInSeconds(final int rememberMeExpirationPolicyInSeconds) {
		this.rememberMeExpirationPolicyInSeconds = rememberMeExpirationPolicyInSeconds;
	}

}