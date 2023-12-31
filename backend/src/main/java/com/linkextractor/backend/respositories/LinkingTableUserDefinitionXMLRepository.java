package com.linkextractor.backend.respositories;

import com.linkextractor.backend.models.User;
import com.linkextractor.backend.models.UserDefinitionXMLTable;
import com.linkextractor.backend.models.XMLBron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LinkingTableUserDefinitionXMLRepository extends JpaRepository<UserDefinitionXMLTable, Integer> {
    UserDefinitionXMLTable save(UserDefinitionXMLTable userDefinitionXMLTable);
    List<UserDefinitionXMLTable> findByUserAndXmlbron(User user, XMLBron xmlbron);
}