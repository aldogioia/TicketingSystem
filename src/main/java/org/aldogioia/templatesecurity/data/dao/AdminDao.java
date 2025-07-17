package org.aldogioia.templatesecurity.data.dao;

import org.aldogioia.templatesecurity.data.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminDao extends JpaRepository<Admin, String> {
}
