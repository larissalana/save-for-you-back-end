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
@Table(name = "tb_cards")
@EntityListeners(AuditingEntityListener.class)
public class CardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String number;

    private String holderName;

    private String expirationDate;

    private String securityCode;

    private String brand = "BRAND";

    private BigDecimal creditLimit;

    private BigDecimal balance;

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
}