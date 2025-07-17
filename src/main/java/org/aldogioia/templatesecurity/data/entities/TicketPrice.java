package org.aldogioia.templatesecurity.data.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.aldogioia.templatesecurity.security.logging.Auditable;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Entity
@NoArgsConstructor
@Table(name = "ticket_prices")
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
public class TicketPrice extends Auditable {
    @Id
    @UuidGenerator
    private String id;

    private Double price;

    @ManyToOne
    @JoinColumn(name = "ticket_type_id")
    @ToString.Exclude
    private TicketType ticketType;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    @ToString.Exclude
    private Ticket ticket;
}
