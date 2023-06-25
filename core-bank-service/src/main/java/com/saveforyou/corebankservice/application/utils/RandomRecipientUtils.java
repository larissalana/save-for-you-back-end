package com.saveforyou.corebankservice.application.utils;

import com.saveforyou.corebankservice.domain.transaction.model.Recipient;
import lombok.experimental.UtilityClass;

import java.util.Random;

@UtilityClass
public class RandomRecipientUtils {

    public static Recipient generateRandomRecipient(){
        var random = new Random();
        var randomIndex = random.nextInt(10);
        return Recipient.recipientData().get(randomIndex);
    }
}
