package sanchez.sanchez.sergio.bullkeeper.persistence.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.PendingDeviceEntity;

@Repository
public interface PendingDeviceRepository extends MongoRepository<PendingDeviceEntity, ObjectId> {
	
	Long deleteByOwner(ObjectId id);
	
	Long deleteByDeviceId(String deviceId);
	
	PendingDeviceEntity findByDeviceId(String deviceId);
	
	PendingDeviceEntity findByDeviceIdAndOwner(String deviceId, ObjectId owner);
	
	List<PendingDeviceEntity>  findByOwner(ObjectId owner);

}
