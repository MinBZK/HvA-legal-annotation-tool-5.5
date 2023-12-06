<<<<<<< HEAD:backend/src/main/java/com/linkextractor/backend/respositories/BrondefinitieRepository.java
package com.linkextractor.backend.respositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.linkextractor.backend.models.Brondefinitie;

public interface BrondefinitieRepository extends CrudRepository<Brondefinitie, Integer>{
    
    List<Brondefinitie> findAll();

    Brondefinitie findById(int brondefinitie_id);

    Brondefinitie save(Brondefinitie brondefinitie);

    void deleteById(int brondefinitie_id);
}
=======
//package com.linkextractor.respositories;
//
//import java.util.List;
//
//import org.springframework.data.repository.CrudRepository;
//
//import com.linkextractor.models.Brondefinitie;
//
//public interface BrondefinitieRepository extends CrudRepository<Brondefinitie, Integer>{
//
//    List<Brondefinitie> findAll();
//
//    Brondefinitie findOne(int brondefinitie_id);
//
//    Brondefinitie save(Brondefinitie brondefinitie);
//
//    void deleteById(int brondefinitie_id);
//}
>>>>>>> origin/login-signup-flow:backend/src/main/java/com/linkextractor/respositories/BrondefinitieRepository.java
