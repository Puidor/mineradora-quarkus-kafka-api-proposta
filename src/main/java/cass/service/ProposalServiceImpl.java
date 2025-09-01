package cass.service;

import cass.dto.ProposalDTO;
import cass.dto.ProposalDetailsDTO;
import cass.entity.ProposalEntity;
import cass.message.KafkaEvent;
import cass.repository.ProposalRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.Date;

@ApplicationScoped
public class ProposalServiceImpl implements ProposalService {

    @Inject
    ProposalRepository proposalRepository;

    @Inject
    KafkaEvent kafkaMessages;

    @Override
    public ProposalDetailsDTO findFullProposal(Long id) {
        ProposalEntity proposal = proposalRepository.findById(id);

        return ProposalDetailsDTO.builder()
                .proposalId(proposal.getId())
                .proposalValidityDays(proposal.getProposalValidityDays())
                .country(proposal.getCountry())
                .priceTonne(proposal.getPriceTonne())
                .customer(proposal.getCustomer())
                .tonnes(proposal.getTonnes())
                .build();
    }

    @Override
    @Transactional
    public void createNewProposal(ProposalDetailsDTO proposalDetailsDTO) {

        ProposalDTO proposal = buildAndSaveNewProposal(proposalDetailsDTO);
        kafkaMessages.sendProposalRequest(proposal);
    }

    @Override
    @Transactional
    public void removeProposal(Long id) {
        proposalRepository.deleteById(id);
    }

    @Transactional
    public ProposalDTO buildAndSaveNewProposal (ProposalDetailsDTO proposalDetailsDTO){

        try{
            ProposalEntity proposal = new ProposalEntity();

            proposal.setCreated(new Date());
            proposal.setProposalValidityDays(proposalDetailsDTO.getProposalValidityDays());
            proposal.setCountry(proposalDetailsDTO.getCountry());
            proposal.setPriceTonne(proposalDetailsDTO.getPriceTonne());
            proposal.setCustomer(proposalDetailsDTO.getCustomer());
            proposal.setTonnes(proposalDetailsDTO.getTonnes());

            proposalRepository.persist(proposal);
            return ProposalDTO.builder()
                    .proposalId(proposalRepository.findByCustumer(proposal.getCustomer()).get().getId())
                    .priceTonne(proposal.getPriceTonne())
                    .customer(proposal.getCustomer())
                    .build();
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Erro ao salvar proposta: " + e.getMessage());
        }
    }
}
