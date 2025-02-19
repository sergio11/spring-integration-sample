package sanchez.sanchez.sergio.bullkeeper.persistence.repository;

import org.bson.types.ObjectId;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.GeofenceAlertEntity;

/**
 * Geofence Repository
 * @author sergio
 */
public interface GeofenceRepositoryCustom {
	
	/**
	 * Find Alerts
	 * @param kid
	 * @param geofence
	 * @return
	 */
	Iterable<GeofenceAlertEntity> findAlerts(final ObjectId kid, final ObjectId geofence);
	
	
	/**
	 * Delete Alerts
	 * @param kid
	 * @param geofence
	 */
	void deleteAlerts(final ObjectId kid, final ObjectId geofence);
	
	/**
	 * Enable Geofence
	 * @param kid
	 * @param geofence
	 */
	void enableGeofence(final ObjectId kid, final ObjectId geofence);
	
	/**
	 * Disable Geofence
	 * @param kid
	 * @param geofence
	 */
	void disableGeofence(final ObjectId kid, final ObjectId geofence);
	
	
	
}
