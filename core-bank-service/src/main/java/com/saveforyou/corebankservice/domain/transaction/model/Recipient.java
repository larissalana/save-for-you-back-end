package com.saveforyou.corebankservice.domain.transaction.model;

import com.saveforyou.corebankservice.application.enums.RecipientType;
import io.swagger.model.RecipientApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.saveforyou.corebankservice.application.enums.RecipientType.ACCOUNT_EXTERNAL;

@Getter
@Setter
@Builder
public class Recipient {

    private RecipientType type;
    private UUID id;
    private String description;

    public static Recipient from(RecipientApiModel recipient) {
        return Recipient.builder()
                .id(recipient.getId())
                .type(RecipientType.valueOf(recipient.getType()))
                .description(recipient.getDescription())
                .build();
    }

    public static List<Recipient> recipientData() {
        var recipients = new ArrayList<Recipient>();
        recipients.add(Recipient.builder().type(ACCOUNT_EXTERNAL).id(UUID.randomUUID()).description("Shell").build());
        recipients.add(Recipient.builder().type(ACCOUNT_EXTERNAL).id(UUID.randomUUID()).description("Sephora").build());
        recipients.add(Recipient.builder().type(ACCOUNT_EXTERNAL).id(UUID.randomUUID()).description("Amazon").build());
        recipients.add(Recipient.builder().type(ACCOUNT_EXTERNAL).id(UUID.randomUUID()).description("Apple").build());
        recipients.add(Recipient.builder().type(ACCOUNT_EXTERNAL).id(UUID.randomUUID()).description("Microsoft").build());
        recipients.add(Recipient.builder().type(ACCOUNT_EXTERNAL).id(UUID.randomUUID()).description("Padaria").build());
        recipients.add(Recipient.builder().type(ACCOUNT_EXTERNAL).id(UUID.randomUUID()).description("Restaurante").build());
        recipients.add(Recipient.builder().type(ACCOUNT_EXTERNAL).id(UUID.randomUUID()).description("Mc Donalds").build());
        recipients.add(Recipient.builder().type(ACCOUNT_EXTERNAL).id(UUID.randomUUID()).description("Burguer King").build());
        recipients.add(Recipient.builder().type(ACCOUNT_EXTERNAL).id(UUID.randomUUID()).description("kopenhagen").build());

        return recipients;
    }
}
