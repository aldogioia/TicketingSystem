package org.aldogioia.templatesecurity.data.dao;

import org.aldogioia.templatesecurity.data.entities.Validator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValidatorDao extends JpaRepository<Validator, String> {
}
