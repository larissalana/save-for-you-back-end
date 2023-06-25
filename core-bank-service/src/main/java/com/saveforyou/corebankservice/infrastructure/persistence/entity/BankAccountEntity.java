package com.saveforyou.corebankservice.infrastructure.persistence.entity;

import com.saveforyou.corebankservice.application.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_bank_accounts")
@EntityListeners(AuditingEntityListener.class)
public class BankAccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String branch;

    private String accountNumber;

    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private BankAccountType type;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne
    private ClientEntity client;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public enum BankAccountType {
        CHECKING_ACCOUNT,
        SAVING_ACCOUNT,
        SALARY_ACCOUNT
    }

    public void deposit(BigDecimal amount){
        this.balance = this.balance.add(amount);
    }

    public void withdraw(BigDecimal amount){
        if(this.balance.compareTo(amount) <= 0)
            throw new IllegalArgumentException("Insufficient balance");

        this.balance = this.balance.subtract(amount);
    }
}