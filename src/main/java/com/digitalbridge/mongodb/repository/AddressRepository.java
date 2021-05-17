package com.digitalbridge.mongodb.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import com.digitalbridge.domain.Address;

@RepositoryRestResource(collectionResourceRel = "address", path = "address")
@PreAuthorize("hasRole('ROLE_USER')")
public interface AddressRepository extends MongoRepository<Address, String> {

	List<Address> findByLocationNear(@Param("point") Point point,
			@Param("distance") Distance distance, Pageable pageable);

}
