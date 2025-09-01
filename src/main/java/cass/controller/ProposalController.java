package cass.controller;

import cass.dto.ProposalDetailsDTO;
import cass.service.ProposalService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/api/proposal")
public class ProposalController {

    private final Logger LOG = LoggerFactory.getLogger(ProposalController.class);

    @Inject
    ProposalService proposalService;

    @GET
    @Path("/{id}")
    public ProposalDetailsDTO findDetailsProposal(@PathParam("id") long id){
        return proposalService.findFullProposal(id);
    }

    @POST
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
