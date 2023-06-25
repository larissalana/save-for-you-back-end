package com.saveforyou.savinggoalsservice.domain.transaction.processor;

import com.saveforyou.savinggoalsservice.application.enums.CardType;
import com.saveforyou.savinggoalsservice.domain.savingrule.automation.model.SavingRuleAutomation;
import com.saveforyou.savinggoalsservice.domain.savingrule.model.SavingRule;
import com.saveforyou.savinggoalsservice.domain.transaction.model.Transaction;
import com.saveforyou.savinggoalsservice.infrastructure.client.CoreBankClient;
import com.saveforyou.savinggoalsservice.infrastructure.client.model.CardChargeRequest;
import io.swagger.model.TransactionNotificationApiModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

import static com.saveforyou.savinggoalsservice.application.enums.CardType.BOTH;
import static com.saveforyou.savinggoalsservice.application.enums.DepositPropertiesKeys.CARD_TYPE;
import static com.saveforyou.savinggoalsservice.application.enums.DepositPropertiesKeys.START_DATE;
import static com.saveforyou.savinggoalsservice.application.utils.DateUtils.convertStringToLocalDate;
import static com.saveforyou.savinggoalsservice.domain.savingrule.model.SavingRule.Category.DEPOSIT;
import static com.saveforyou.savinggoalsservice.domain.savingrule.model.SavingRule.Status.ACTIVE;
import static com.saveforyou.savinggoalsservice.domain.savingrule.model.SavingRule.Type.ROUNDING;
import static io.swagger.model.TransactionNotificationApiModel.TypeEnum.CREDIT_CHARGE;
import static io.swagger.model.TransactionNotificationApiModel.TypeEnum.DEBIT_CHARGE;

@Component
@RequiredArgsConstructor
public class RoundingDepositTransactionProcessorImpl implements TransactionProcessor {

    private final CoreBankClient coreBankClient;

    @Override
    public boolean canProcess(SavingRule savingRule, TransactionNotificationApiModel transactionNotificationApiModel) {
        return DEPOSIT.equals(savingRule.getCategory())
                && ROUNDING.equals(savingRule.getType())
                && ACTIVE.equals(savingRule.getStatus())
                && List.of(DEBIT_CHARGE, CREDIT_CHARGE).contains(transactionNotificationApiModel.getType());
    }

    public Transaction process(SavingRuleAutomation savingRuleAutomation, TransactionNotificationApiModel transactionNotificationApiModel) {
        var configurations = savingRuleAutomation.getConfigurations();
        var startDate = convertStringToLocalDate(configurations.get(START_DATE.getKey()).toString());
        var cardType = CardType.valueOf(configurations.get(CARD_TYPE.getKey()).toString());
        var typeNormalized = getTypeNotificationNormalized(transactionNotificationApiModel.getType());

        if ((LocalDate.now().isAfter(startDate) || LocalDate.now().isEqual(startDate))
                && isCardTypeValid(cardType, typeNormalized)) {
            var chargeAmount = getChargeAmount(transactionNotificationApiModel.getAmount());

            if(chargeAmount.compareTo(BigDecimal.ZERO) > 0){
                var cardChargeRequest = CardChargeRequest.from(savingRuleAutomation.getSavingGoal(), chargeAmount, typeNormalized);

                coreBankClient.processCardChargeInternal(cardChargeRequest, savingRuleAutomation.getSavingGoal().getClientId());

                return Transaction.from(savingRuleAutomation, chargeAmount);
            }
        }
        return null;
    }

    private boolean isCardTypeValid(CardType cardType, String typeNormalized) {
        return cardType.name().equals(typeNormalized) || BOTH.name().equals(cardType.name());
    }

    private String getTypeNotificationNormalized(TransactionNotificationApiModel.TypeEnum type){
        return type.name().replace("_CHARGE", "");
    }

    private BigDecimal getChargeAmount(BigDecimal amountTransactionNotification){
        return amountTransactionNotification.setScale(0, RoundingMode.UP).subtract(amountTransactionNotification);
    }
}