package com.saveforyou.corebankservice.controller.bankaccount;

import com.saveforyou.corebankservice.controller.bankaccount.mapper.BankAccountApiMapper;
import com.saveforyou.corebankservice.domain.bankaccount.BankAccountService;
import io.swagger.api.BankAccountApi;
import io.swagger.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class BankAccountController implements BankAccountApi {

    private final BankAccountApiMapper bankAccountApiMapper;
    private final BankAccountService bankAccountService;

    @Override
    public ResponseEntity<OpenBankAccountResponse> openBankAccount(OpenBankAccountRequest openBankAccountRequest){
        var response = bankAccountService.openBankAccount(openBankAccountRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{accountNumber}").buildAndExpand(response.getAccountNumber()).toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @Override
    public ResponseEntity<BankAccountApiModel> getBankAccountInfo(String branch, String accountNumber){
        var response = bankAccountService.getByBranchAndAccountNumber(branch, accountNumber);
        return  ResponseEntity.ok(bankAccountApiMapper.toApiModel(response));
    }

    @Override
    public ResponseEntity<BankAccountApiModel> getBankAccountInfoByClientId(UUID clientId){
        var response = bankAccountService.getByClientId(clientId);
        return  ResponseEntity.ok(bankAccountApiMapper.toApiModel(response));
    }

    @Override
    public ResponseEntity<BankAccountApiModel> depositAmount(String branch, String accountNumber, DepositApiModel depositApiModel){
        var response = bankAccountService.deposit(branch, accountNumber, depositApiModel);
        return ResponseEntity.ok(bankAccountApiMapper.toApiModel(response));
    }

    @Override
    public ResponseEntity<Void> transferAmountInternal(UUID clientId, TransferInternalApiModel transferInternalApiModel){
        bankAccountService.transferInternal(clientId, transferInternalApiModel);
        return ResponseEntity.ok().build();
    }
}