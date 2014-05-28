package org.esupportail.cas.addon.controllers;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.jasig.cas.CentralAuthenticationService;
import org.jasig.cas.ticket.Ticket;
import org.jasig.cas.ticket.TicketGrantingTicket;
import org.jasig.cas.ticket.registry.TicketRegistry;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class AdminRevocationController extends AbstractController {

	@NotNull
	private CentralAuthenticationService centralAuthenticationService;
	private final TicketRegistry ticketRegistry;

	public AdminRevocationController(final TicketRegistry ticketRegistry) {
		this.ticketRegistry = ticketRegistry;
	}

	@Override
	protected ModelAndView handleRequestInternal(final HttpServletRequest request, final HttpServletResponse response) throws Exception {

		ModelMap model = new ModelMap();

		String targetUser = request.getParameter("user");

		if(targetUser != null) {
			try {
				final Collection<Ticket> tickets = this.ticketRegistry.getTickets();

				int ticketDestroyed = 0;
				for(Ticket ticket : tickets) {
					if(ticket instanceof TicketGrantingTicket) {

						String ticketOwner = ((TicketGrantingTicket) ticket).getAuthentication().getPrincipal().getId();

						if(ticketOwner.equalsIgnoreCase(targetUser)) {
							this.centralAuthenticationService.destroyTicketGrantingTicket(ticket.getId());
							ticketDestroyed++;
						}
					}
				}
				model.put("ticketDestroyed", ticketDestroyed);
				model.put("user", targetUser);

			} catch (UnsupportedOperationException e) {
				e.printStackTrace();
				model.put("error", true);
			}
		}
		return new ModelAndView("adminRevocationView", model);
	}

	public void setCentralAuthenticationService(
			final CentralAuthenticationService centralAuthenticationService) {
		this.centralAuthenticationService = centralAuthenticationService;
	}

}