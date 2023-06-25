package com.saveforyou.savinggoalsservice.domain.savingrule.processor.factory;

import com.saveforyou.savinggoalsservice.domain.savingrule.model.SavingRule;
import com.saveforyou.savinggoalsservice.domain.savingrule.processor.SavingRuleProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SavingRuleProcessorFactory {

    private final List<SavingRuleProcessor> processors;

    public SavingRuleProcessor getProcessor(SavingRule savingRule) {
        return processors.stream()
                .filter(c -> c.canProcess(savingRule))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No processor found for type: " + savingRule.getType()));
    }
}