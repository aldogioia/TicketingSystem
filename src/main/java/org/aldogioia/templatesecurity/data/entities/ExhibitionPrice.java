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
@Table(name = "exhibition_prices", uniqueConstraints = @UniqueConstraint(
        name = "unique_exhibition_ticket_type",
        columnNames = {"exhibition_id", "ticket_type_id"}
))
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
public class ExhibitionPrice extends Auditable {
    @Id
    @UuidGenerator
    private String id;

    @Column(nullable = false)
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exhibition_id", nullable = false)
    @ToString.Exclude
    private Exhibition exhibition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_type_id", nullable = false)
    @ToString.Exclude
    private TicketType ticketType;
}
