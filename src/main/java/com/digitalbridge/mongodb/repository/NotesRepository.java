package com.digitalbridge.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import com.digitalbridge.domain.Notes;

/**
 * <p>NotesRepository interface.</p>
 *
 * @author rajakolli
 * @version 1:0
 */
@RepositoryRestResource(collectionResourceRel = "notes", path = "notes")
@PreAuthorize("hasRole('ROLE_USER')")
public interface NotesRepository extends MongoRepository<Notes, String> { 

}
