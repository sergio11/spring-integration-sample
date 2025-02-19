package sanchez.sanchez.sergio.bullkeeper.persistence.repository;

import java.util.Collection;

import org.bson.types.ObjectId;

/**
 *
 * @author sergio
 */
public interface ScheduledBlockRepositoryCustom {
	
	/**
	 * Enable Scheduled Blocks
	 * @param ids
	 */
	void enableScheduledBlocks(final Collection<ObjectId> ids);
	
	/**
	 * Disable Scheduled Blocks
	 * @param ids
	 */
	void disableScheduledBlocks(final Collection<ObjectId> ids);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	String getImageForScheduledBlock(final ObjectId id);
	
}
