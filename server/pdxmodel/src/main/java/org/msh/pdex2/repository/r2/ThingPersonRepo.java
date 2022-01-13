package org.msh.pdex2.repository.r2;

import java.util.List;

import org.msh.pdex2.model.r2.Concept;
import org.msh.pdex2.model.r2.ThingPerson;
import org.springframework.data.repository.CrudRepository;

public interface ThingPersonRepo extends CrudRepository<ThingPerson, Long> {

	List<ThingPerson> findByConcept(Concept concept);

}
