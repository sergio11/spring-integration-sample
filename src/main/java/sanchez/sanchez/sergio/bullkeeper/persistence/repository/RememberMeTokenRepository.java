package sanchez.sanchez.sergio.bullkeeper.persistence.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.RememberMeTokenEntity;


/**
 * @author sergio
 */
@Repository
public interface RememberMeTokenRepository extends MongoRepository<RememberMeTokenEntity, ObjectId> {
	RememberMeTokenEntity findBySeries(String series);
    Iterable<RememberMeTokenEntity> findByUsername(String username);
}
