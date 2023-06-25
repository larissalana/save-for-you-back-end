package com.saveforyou.corebankservice.controller.card;

import com.saveforyou.corebankservice.domain.card.CardService;
import io.swagger.api.CardApi;
import io.swagger.model.CardChargeApiModel;
import io.swagger.model.CardChargeInternalApiModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CardController implements CardApi {

    private final CardService cardService;

    @Override
    public ResponseEntity<Void> processCardCharge(UUID clientId, CardChargeApiModel cardChargeApiModel){
        cardService.processCharge(clientId, cardChargeApiModel);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> processCardChargeInternal(UUID clientId, CardChargeInternalApiModel cardChargeInternalApiModel){
        cardService.processCardChargeInternal(clientId, cardChargeInternalApiModel);
        return ResponseEntity.ok().build();
    }
}