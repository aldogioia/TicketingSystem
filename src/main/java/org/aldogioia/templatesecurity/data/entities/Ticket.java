package org.aldogioia.templatesecurity.data.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.aldogioia.templatesecurity.data.enumerators.TicketStatus;
import org.aldogioia.templatesecurity.security.logging.Auditable;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(name = "tickets")
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
public class Ticket extends Auditable {
    @Id
    @UuidGenerator
    private String id;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    @Column(name = "issued_on", nullable = false)
    private LocalDateTime issuedOn;

    @Column(name = "closed_on")
    private LocalDateTime closedOn;

    @Column(name= "price_at_purchase", nullable = false)
    private Double priceAtPurchase;

    @Column(name= "exhibition_title", nullable = false)
    private String exhibitionTitle;

    @Column(name = "ticket_type_name", nullable = false)
    private String ticketType;

    @Column(name = "people_number", nullable = false)
    private Integer peopleNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exhibition_price_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @ToString.Exclude
    private ExhibitionPrice exhibitionPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User user;
}
