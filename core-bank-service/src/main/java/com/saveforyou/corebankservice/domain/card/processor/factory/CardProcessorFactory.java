package com.saveforyou.corebankservice.domain.card.processor.factory;

import com.saveforyou.corebankservice.domain.card.processor.CardProcessor;
import io.swagger.model.ChargeTypeApiModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardProcessorFactory {

    private final List<CardProcessor> processors;

    public CardProcessor getProcessor(ChargeTypeApiModel chargeType) {
        return processors.stream()
                .filter(c -> c.canProcess(chargeType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No processor found for type: " + chargeType));
    }
}