package org.msh.pdex2.repository.newRegistration;

import org.msh.pdex2.model.newRegistration.ExternalUsers;
import org.springframework.data.repository.CrudRepository;

public interface ExternalUsersRepo extends CrudRepository<ExternalUsers, Long> {
	ExternalUsers findByEmail(String email);
	ExternalUsers findByUserId(long id);
}
