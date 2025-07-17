package org.aldogioia.templatesecurity.data.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@DiscriminatorValue("admins")
@EqualsAndHashCode(callSuper = true)
public class Admin extends User {
}
