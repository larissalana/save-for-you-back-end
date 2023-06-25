package com.saveforyou.corebankservice.domain.card.processor;

import io.swagger.model.CardChargeApiModel;
import io.swagger.model.CardChargeInternalApiModel;
import io.swagger.model.ChargeTypeApiModel;

import java.util.UUID;

public interface CardProcessor {

    boolean canProcess(ChargeTypeApiModel chargeTypeApiModel);

    void processCharge(UUID clientId, CardChargeApiModel cardChargeApiModel);

    void processChargeInternal(UUID clientId, CardChargeInternalApiModel cardChargeInternalApiModel);
}
