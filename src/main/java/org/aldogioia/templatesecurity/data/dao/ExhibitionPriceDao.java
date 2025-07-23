package org.aldogioia.templatesecurity.data.dao;

import org.aldogioia.templatesecurity.data.entities.ExhibitionPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExhibitionPriceDao extends JpaRepository<ExhibitionPrice, String> {
}
