package com.saveforyou.savinggoalsservice.domain.transaction.processor;

import com.saveforyou.savinggoalsservice.application.enums.DepositValueType;
import com.saveforyou.savinggoalsservice.application.exceptions.BadRequestException;
import com.saveforyou.savinggoalsservice.domain.savingrule.automation.model.SavingRuleAutomation;
import com.saveforyou.savinggoalsservice.domain.savingrule.model.SavingRule;
import com.saveforyou.savinggoalsservice.domain.transaction.model.Transaction;
import com.saveforyou.savinggoalsservice.infrastructure.client.CoreBankClient;
import com.saveforyou.savinggoalsservice.infrastructure.client.model.TransferRequest;
import io.swagger.model.TransactionNotificationApiModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Map;

import static com.saveforyou.savinggoalsservice.application.enums.DepositPropertiesKeys.*;
import static com.saveforyou.savinggoalsservice.application.enums.DepositValueType.PERCENTAGE;
import static com.saveforyou.savinggoalsservice.application.utils.DateUtils.convertStringToLocalDate;
import static com.saveforyou.savinggoalsservice.domain.savingrule.model.SavingRule.Category.DEPOSIT;
import static com.saveforyou.savinggoalsservice.domain.savingrule.model.SavingRule.Status.ACTIVE;
import static com.saveforyou.savinggoalsservice.domain.savingrule.model.SavingRule.Type.SALARY;
import static io.swagger.model.TransactionNotificationApiModel.TypeEnum.SALARY_DEPOSIT;

@Component
@RequiredArgsConstructor
public class SalaryDepositTransactionProcessorImpl implements TransactionProcessor {

    public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);
    public static final Integer SCALE = 2;

    private static final String AMOUNT_INVALID = "A automatização dessa transação não pode ser realizada pois o valor de depósito configurado é maior do que o valor do salário depositado.";

    private final CoreBankClient coreBankClient;

    @Override
    public boolean canProcess(SavingRule savingRule, TransactionNotificationApiModel transactionNotificationApiModel) {
        return DEPOSIT.equals(savingRule.getCategory())
                && SALARY.equals(savingRule.getType())
                && ACTIVE.equals(savingRule.getStatus())
                && SALARY_DEPOSIT.equals(transactionNotificationApiModel.getType());
    }

    public Transaction process(SavingRuleAutomation savingRuleAutomation, TransactionNotificationApiModel transactionNotificationApiModel) {
        var configurations = savingRuleAutomation.getConfigurations();
        var startDate = convertStringToLocalDate(configurations.get(START_DATE.getKey()).toString());

        if (LocalDate.now().isAfter(startDate) || LocalDate.now().isEqual(startDate)) {
            var valueToBeTransfer = getValueToBeTransfer(configurations, transactionNotificationApiModel.getAmount());
            var transferRequest = TransferRequest.from(savingRuleAutomation.getSavingGoal(), valueToBeTransfer);

            coreBankClient.processTransferAmountInternal(transferRequest, savingRuleAutomation.getSavingGoal().getClientId());

            return Transaction.from(savingRuleAutomation, valueToBeTransfer);
        }
        return null;
    }

    private BigDecimal getValueToBeTransfer(Map<String, Object> configurations, BigDecimal amountTransactionNotification){
        var depositValueType = DepositValueType.valueOf(configurations.get(DEPOSIT_VALUE_TYPE.getKey()).toString());
        var depositValue = new BigDecimal(configurations.get(DEPOSIT_VALUE.getKey()).toString());

        return isPercentageType(depositValueType)
                ? depositPercentageValue(depositValue, amountTransactionNotification)
                : depositAmountValue(depositValue, amountTransactionNotification);
    }

    private BigDecimal depositPercentageValue(BigDecimal depositValue, BigDecimal amountTransactionNotification) {
        return amountTransactionNotification.multiply(depositValue).divide(ONE_HUNDRED, SCALE, RoundingMode.HALF_UP);
    }

    private BigDecimal depositAmountValue(BigDecimal depositValue, BigDecimal amountTransactionNotification) {
        if(amountTransactionNotification.compareTo(depositValue) < 0 ){
            throw new BadRequestException(AMOUNT_INVALID);
        }
        return depositValue;
    }

    private boolean isPercentageType(DepositValueType depositValueType) {
        return PERCENTAGE.name().equals(depositValueType.name());
    }
}