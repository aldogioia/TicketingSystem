package org.aldogioia.templatesecurity.data.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.aldogioia.templatesecurity.security.logging.Auditable;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "ticket_types")
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
public class TicketType extends Auditable {
    @Id
    @UuidGenerator
    private String id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "is_multiple_entry_allowed", nullable = false)
    private Boolean isMultipleEntryAllowed;

    @OneToMany(mappedBy = "ticketType", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<ExhibitionPrice> exhibitionPrices;
}
