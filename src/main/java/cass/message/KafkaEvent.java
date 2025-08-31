package cass.message;

import cass.dto.ProposalDTO;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class KafkaEvent {

    private final Logger LOG = LoggerFactory.getLogger(KafkaEvent.class);

    @Channel("proposal")
    Emitter<ProposalDTO> proposalRequestEmitter;

    public void sendProposalRequest(ProposalDTO proposalDTO) {
        LOG.info("Enviando Nova Proposta para Tópico Kafka: {}", proposalDTO);
        proposalRequestEmitter.send(proposalDTO).toCompletableFuture().join();
    }
}
