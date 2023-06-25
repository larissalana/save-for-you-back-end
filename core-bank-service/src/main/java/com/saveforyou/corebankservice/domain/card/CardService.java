package com.saveforyou.corebankservice.domain.card;

import com.saveforyou.corebankservice.domain.card.mapper.CardMapper;
import com.saveforyou.corebankservice.domain.card.processor.factory.CardProcessorFactory;
import com.saveforyou.corebankservice.domain.client.model.Client;
import com.saveforyou.corebankservice.infrastructure.persistence.repository.CardRepository;
import io.swagger.model.CardChargeApiModel;
import io.swagger.model.CardChargeInternalApiModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final CardMapper cardMapper;
    private final CardProcessorFactory cardProcessorFactory;

    public void createCard(Client client){
        var cardToCreate = cardMapper.toCreate(client);
        var cardSaved = cardRepository.save(cardMapper.toEntity(cardToCreate));

        cardMapper.toDomainModel(cardSaved);
    }

    public void processCharge(UUID clientId, CardChargeApiModel cardChargeApiModel){
        var cardProcessor = cardProcessorFactory.getProcessor(cardChargeApiModel.getType());
        cardProcessor.processCharge(clientId, cardChargeApiModel);
    }

    public void processCardChargeInternal(UUID clientId, CardChargeInternalApiModel cardChargeInternalApiModel) {
        var cardProcessor = cardProcessorFactory.getProcessor(cardChargeInternalApiModel.getType());
        cardProcessor.processChargeInternal(clientId, cardChargeInternalApiModel);
    }
}