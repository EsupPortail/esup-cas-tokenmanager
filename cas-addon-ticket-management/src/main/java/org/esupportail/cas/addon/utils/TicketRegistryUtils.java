package org.esupportail.cas.addon.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.esupportail.cas.addon.model.JsonTicket;
import org.esupportail.cas.addon.model.TicketOwner;

import org.jasig.cas.ticket.Ticket;
import org.jasig.cas.ticket.TicketGrantingTicket;
import org.jasig.cas.ticket.registry.TicketRegistry;

import org.springframework.beans.factory.annotation.Autowired;

public class TicketRegistryUtils {

	@Autowired
	private TicketRegistry ticketRegistry;

	public List<Ticket> listTicketForUser(String user) {

		List<Ticket> userTickets = new ArrayList<Ticket>();

		try {

			final Collection<Ticket> tickets = this.ticketRegistry.getTickets();
			for(final Ticket ticket : tickets) {

				if(ticket instanceof TicketGrantingTicket && !ticket.isExpired()) {

					TicketGrantingTicket tgt = (TicketGrantingTicket) ticket;
					String ticketOwner = tgt.getAuthentication().getPrincipal().getId();

					if(ticketOwner.equalsIgnoreCase(user)) {
						userTickets.add(ticket);
					}
				}
			}
		} catch (final UnsupportedOperationException e) {
			e.printStackTrace();
		}

		return userTickets;
	}

	public List<JsonTicket> listTicketToJson(String user) {

		List<JsonTicket> userTickets = new ArrayList<JsonTicket>();

		try {

			final Collection<Ticket> tickets = this.ticketRegistry.getTickets();
			for(final Ticket ticket : tickets) {

				if(ticket instanceof TicketGrantingTicket && !ticket.isExpired()) {

					TicketGrantingTicket tgt = (TicketGrantingTicket) ticket;
					String ticketOwner = tgt.getAuthentication().getPrincipal().getId();

					if(ticketOwner.equalsIgnoreCase(user)) {
						userTickets.add(new JsonTicket(tgt));
					}
				}
			}
		} catch (final UnsupportedOperationException e) {
			e.printStackTrace();
		}

		return userTickets;
	}

	public String ticketBelongTo(String ticketId) {

		try {

			final Collection<Ticket> tickets = this.ticketRegistry.getTickets();
			for(final Ticket ticket : tickets) {

				if(ticketId.equalsIgnoreCase(ticket.getId())) {

					if(ticket instanceof TicketGrantingTicket) {

						TicketGrantingTicket tgt = (TicketGrantingTicket) ticket;
						return tgt.getAuthentication().getPrincipal().getId();
					}
				}
			}

		} catch (final UnsupportedOperationException e) {
			e.printStackTrace();
		}

		return "";
	}
	
}