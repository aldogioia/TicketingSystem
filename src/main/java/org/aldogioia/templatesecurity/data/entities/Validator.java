package org.aldogioia.templatesecurity.data.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@DiscriminatorValue("validators")
@EqualsAndHashCode(callSuper = true)
public class Validator extends User {
}
