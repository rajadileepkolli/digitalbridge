package com.digitalbridge.mongodb.repository;

import java.util.List;
import java.util.concurrent.Future;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.geo.Box;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;

import com.digitalbridge.domain.AssetWrapper;

/**
 * <p>
 * AssetWrapperRepository interface.
 * </p>
 *
 * @author rajakolli
 * @version 1: 0
 */
@RepositoryRestResource(collectionResourceRel = "assets", path = "assetwrapper")
@PreAuthorize("hasRole('ROLE_USER')")
public interface AssetWrapperRepository extends MongoRepository<AssetWrapper, String> {

	@Async
	Page<AssetWrapper> findAll(Pageable pageable);

	@Async
	Future<AssetWrapper> findByAddressLocationNull();

	<S extends AssetWrapper> S save(S s);

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	void delete(String aString);

	List<AssetWrapper> findByNotesScoreGreaterThanEqual(@Param("score") Integer score);

	GeoResults<AssetWrapper> findByAddressLocationWithin(Circle circle);

	Slice<AssetWrapper> findByAddressLocationWithinAndAssetNameIsNotNull(Box box);

	List<AssetWrapper> findByCuisine(@Param("cuisine") String cuisine);

	Page<AssetWrapper> queryFirst10ByAssetName(@Param("assetName") String assetName,
			Pageable pageable);

	Slice<AssetWrapper> findTop3ByAssetName(@Param("assetName") String assetName,
			Pageable pageable);

	Page<AssetWrapper> findByIdIn(@Param("assetIds") List<String> assetIds,
			Pageable pageable);

	Page<AssetWrapper> findByAddressLocationNearAndIdIn(@Param("point") Point point,
			@Param("distance") Distance distance,
			@Param("assetIds") List<String> assetIds, Pageable pageable);

	Page<AssetWrapper> findByAddressIdIn(List<String> assetIds, Pageable pageable);

}
