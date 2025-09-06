package cass.controller;

import cass.dto.ProposalDetailsDTO;
import cass.service.ProposalService;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/api/proposal")
@Authenticated
public class ProposalController {

    private final Logger LOG = LoggerFactory.getLogger(ProposalController.class);

    @Inject
    ProposalService proposalService;

    @Inject
    JsonWebToken jsonWebToken;

    @GET
    @Path("/{id}")
    @RolesAllowed({"user", "manager"})
    public ProposalDetailsDTO findDetailsProposal(@PathParam("id") long id){
        return proposalService.findFullProposal(id);
    }

    @POST
    @RolesAllowed("proposal-customer")
    public Response createProposal(ProposalDetailsDTO proposalDetails){
        LOG.info("Criando nova proposta para o cliente: {}", proposalDetails.getCustomer());

        try {
            proposalService.createNewProposal(proposalDetails);
            return Response.ok().build();
        } catch (Exception e) {
            LOG.error("Erro ao criar proposta: {}", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error creating proposal").build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("manager")
    public Response removeProposal(@PathParam("id") long id){
        try {
            proposalService.removeProposal(id);
            return Response.ok().build();
        } catch (Exception e) {
            LOG.error("Erro ao remover proposta: {}", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error removing proposal").build();
        }
    }
}
