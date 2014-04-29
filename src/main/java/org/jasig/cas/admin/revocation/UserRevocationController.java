package org.jasig.cas.admin.revocation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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
    private TicketRegistry ticketRegistry;

    public UserRevocationController(final TicketRegistry ticketRegistry) {
        this.ticketRegistry = ticketRegistry;
    }
    
    @Override
    protected ModelAndView handleRequestInternal(final HttpServletRequest request, final HttpServletResponse response) throws Exception {

        ModelMap model = new ModelMap();
        
        /**
         * Not secured at all
         * Just for prototyping purpose
         */
        String ticketParam = request.getParameter("ticket");
        if(ticketParam != null) {
            centralAuthenticationService.destroyTicketGrantingTicket(ticketParam);
            model.put("destroyTicketMessage", "Ticket : " + ticketParam + " successfully destroyed from the ticket registry");
        }
        
        /**
         * TODO : 
         * Implements ACL and return ticket only for the authenticated user
         */
        Map<String, HashMap<Authentication, Ticket>> userTickets = this.listTicketForUser("not used for the moment");

        model.put("userTickets", userTickets);
  
        return new ModelAndView("revocationView", model);
    }

    private Map<String, HashMap<Authentication, Ticket>> listTicketForUser(String userId) {
        
        Map<String, HashMap<Authentication, Ticket>> userTickets = new HashMap<String, HashMap<Authentication, Ticket>>();
        
        try {

            // Get all ST and TGT from the ticket registry
            final Collection<Ticket> tickets = this.ticketRegistry.getTickets();
            
            for (final Ticket ticket : tickets) {
                
//                Check if the ticket is a TGT and if it is not expired
                if (ticket instanceof TicketGrantingTicket && !ticket.isExpired()) {

                    // Cast Ticket to TicketGrantingTicket to access specific tgt methods
                    TicketGrantingTicket tgt = (TicketGrantingTicket) ticket;
                    String username = tgt.getAuthentication().getPrincipal().getId();
                    
//                    Checking if user already have a ticket list
//                    otherwise create one, and add the associated TGT
                    if(userTickets.containsKey(username)) {
                        userTickets.get(username).put(tgt.getAuthentication(), ticket);
                    } else {
                        HashMap authTickets = new HashMap<Authentication, Ticket>();
                        authTickets.put(tgt.getAuthentication(), ticket);
                        userTickets.put(username, authTickets);
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
    
}