package com.saveforyou.corebankservice.domain.client;

import com.saveforyou.corebankservice.domain.card.CardService;
import com.saveforyou.corebankservice.domain.client.mapper.ClientMapper;
import com.saveforyou.corebankservice.domain.client.model.Client;
import com.saveforyou.corebankservice.domain.user.UserService;
import com.saveforyou.corebankservice.infrastructure.persistence.repository.ClientRepository;
import io.swagger.model.OpenBankAccountRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientMapper clientMapper;
    private final ClientRepository clientRepository;
    private final UserService userService;
    private final CardService cardService;

    public Client create(OpenBankAccountRequest openBankAccountRequest) {
        var clientToCreate = clientMapper.toCreate(openBankAccountRequest);
        var clientSaved = clientRepository.save(clientMapper.toEntity(clientToCreate));
        var clientDomainModel = clientMapper.toDomainModel(clientSaved);

        userService.create(openBankAccountRequest, clientSaved.getId());
        cardService.createCard(clientDomainModel);

        return clientDomainModel;
    }
}
