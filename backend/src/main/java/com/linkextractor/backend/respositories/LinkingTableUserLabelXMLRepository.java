package com.linkextractor.backend.respositories;

import com.linkextractor.backend.models.User;
import com.linkextractor.backend.models.UserLabelXMLTable;
import com.linkextractor.backend.models.XMLBron;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LinkingTableUserLabelXMLRepository extends JpaRepository<UserLabelXMLTable, Integer> {

    UserLabelXMLTable save(UserLabelXMLTable userLabelXMLTable);
    List<UserLabelXMLTable> findByUserAndXmlbron(User user, XMLBron xmlbron);
}
