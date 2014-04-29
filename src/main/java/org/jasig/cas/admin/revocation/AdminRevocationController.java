package org.jasig.cas.admin.revocation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import org.jasig.cas.CentralAuthenticationService;
import org.jasig.cas.ticket.Ticket;
import org.jasig.cas.ticket.TicketGrantingTicket;
import org.jasig.cas.ticket.registry.TicketRegistry;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;


public class AdminRevocationController extends AbstractController {
    
    @NotNull
    private CentralAuthenticationService centralAuthenticationService;
    private TicketRegistry ticketRegistry;

    public AdminRevocationController(final TicketRegistry ticketRegistry) {
        this.ticketRegistry = ticketRegistry;
    }
    
    @Override
    protected ModelAndView handleRequestInternal(final HttpServletRequest request, final HttpServletResponse response) throws Exception {

        ModelMap model = new ModelMap();

        String userParam = request.getParameter("username");
        
        if(userParam != null) {
            
            List<TicketGrantingTicket> ticketList = this.listTicketForUser(userParam);
            
            for(TicketGrantingTicket tgt : ticketList) {
                this.centralAuthenticationService.destroyTicketGrantingTicket(tgt.toString());
            }
            
        }
        
        return new ModelAndView("adminRevocationView", model);
    }
    
    private List<TicketGrantingTicket> listTicketForUser(String username) {
        
        List<TicketGrantingTicket> ticketList = new ArrayList<TicketGrantingTicket>();
        
        final Collection<Ticket> tickets = this.ticketRegistry.getTickets();
        
        for(Ticket ticket : tickets) {
            if(ticket instanceof TicketGrantingTicket && !ticket.isExpired()) {
                
                TicketGrantingTicket tgt = (TicketGrantingTicket) ticket;
                if(username.equalsIgnoreCase(tgt.getAuthentication().getPrincipal().getId())) {
                    ticketList.add(tgt);
                }
            }  
        }
        
        return ticketList;
    }
    
    public void setCentralAuthenticationService(
        final CentralAuthenticationService centralAuthenticationService) {
        this.centralAuthenticationService = centralAuthenticationService;
    }

}