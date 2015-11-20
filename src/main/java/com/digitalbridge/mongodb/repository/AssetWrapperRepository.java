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
 * <p>AssetWrapperRepository interface.</p>
 *
 * @author rajakolli
 * @version 1: 0
 */
@RepositoryRestResource(collectionResourceRel = "assets", path = "assetwrapper")
@PreAuthorize("hasRole('ROLE_USER')")
public interface AssetWrapperRepository extends MongoRepository<AssetWrapper, String> {

	/**
	 * <p> findAll. </p>
	 *
	 * @param pageable a {@link org.springframework.data.domain.Pageable} object.
	 * @return a {@link org.springframework.data.domain.Page} object.
	 */
	@Async
	Page<AssetWrapper> findAll(Pageable pageable);

	/**
	 * <p>
	 * findByAddressLocationNull.
	 * </p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	@Async
	Future<AssetWrapper> findByAddressLocationNull();

	/**
	 * <p>
	 * save.
	 * </p>
	 *
	 * @param s a S object.
	 * @return a S object.
	 */
	<S extends AssetWrapper> S save(S s);

	/**
	 * <p>
	 * delete.
	 * </p>
	 *
	 * @param aString a {@link java.lang.String} object.
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	void delete(String aString);

	/**
	 * <p>
	 * findByGradesScoreGreaterThanEqual.
	 * </p>
	 *
	 * @param score a {@link java.lang.Integer} object.
	 * @return a {@link java.util.List} object.
	 */
	List<AssetWrapper> findByNotesScoreGreaterThanEqual(@Param("score") Integer score);

	/**
	 * <p>
	 * findByAddressLocationWithin.
	 * </p>
	 *
	 * @param circle a {@link org.springframework.data.geo.Circle} object.
	 * @return a {@link org.springframework.data.geo.GeoResults} object.
	 */
	GeoResults<AssetWrapper> findByAddressLocationWithin(Circle circle);

	/**
	 * <p>
	 * findByAddressLocationWithinAndNameIsNotNull.
	 * </p>
	 *
	 * @param box a {@link org.springframework.data.geo.Box} object.
	 * @return a {@link org.springframework.data.domain.Slice} object.
	 */
	Slice<AssetWrapper> findByAddressLocationWithinAndAssetNameIsNotNull(Box box);

	/**
	 * <p>
	 * findByCuisine.
	 * </p>
	 *
	 * @param cuisine a {@link java.lang.String} object.
	 * @return a {@link java.util.List} object.
	 */
	List<AssetWrapper> findByCuisine(@Param("cuisine") String cuisine);

	/**
	 * <p>
	 * queryFirst10ByAssetName.
	 * </p>
	 *
	 * @param assetName a {@link java.lang.String} object.
	 * @param pageable a {@link org.springframework.data.domain.Pageable} object.
	 * @return a {@link org.springframework.data.domain.Page} object.
	 */
	Page<AssetWrapper> queryFirst10ByAssetName(@Param("assetName") String assetName, Pageable pageable);

	/**
	 * <p>
	 * findTop3ByAssetName.
	 * </p>
	 *
	 * @param assetName a {@link java.lang.String} object.
	 * @param pageable a {@link org.springframework.data.domain.Pageable} object.
	 * @return a {@link org.springframework.data.domain.Slice} object.
	 */
	Slice<AssetWrapper> findTop3ByAssetName(@Param("assetName") String assetName, Pageable pageable);

	/**
	 * <p>
	 * findByIdIn.
	 * </p>
	 *
	 * @param assetIds a {@link java.util.List} object.
	 * @param pageable a {@link org.springframework.data.domain.Pageable} object.
	 * @return a {@link org.springframework.data.domain.Page} object.
	 */
	Page<AssetWrapper> findByIdIn(@Param("assetIds") List<String> assetIds, Pageable pageable);
	
	/**
	 * <p>findByAddressLocationNearAndIdIn.</p>
	 *
	 * @param point a {@link org.springframework.data.geo.Point} object.
	 * @param distance a {@link org.springframework.data.geo.Distance} object.
	 * @param assetIds a {@link java.util.List} object.
	 * @param pageable a {@link org.springframework.data.domain.Pageable} object.
	 * @return a {@link org.springframework.data.domain.Page} object.
	 */
	Page<AssetWrapper> findByAddressLocationNearAndIdIn(@Param("point") Point point, @Param("distance") Distance distance,
			@Param("assetIds") List<String> assetIds, Pageable pageable);
	
	/**
	 * <p>findByAddressIdIn.</p>
	 *
	 * @param assetIds a {@link java.util.List} object.
	 * @param pageable a {@link org.springframework.data.domain.Pageable} object.
	 * @return a {@link org.springframework.data.domain.Page} object.
	 */
	Page<AssetWrapper> findByAddressIdIn(List<String> assetIds, Pageable pageable);
	
}
