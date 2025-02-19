package sanchez.sanchez.sergio.bullkeeper.domain.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveGuardianDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveLocationDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.ImageDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.KidDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.KidGuardianDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.LocationDTO;
import sanchez.sanchez.sergio.bullkeeper.web.uploads.models.RequestUploadFile;

import java.util.List;

import org.bson.types.ObjectId;


/**
 * Kid Service
 * @author sergiosanchezsanchez
 *
 */
public interface IKidService {
	
	/**
	 *
	 * @param page
	 * @param size
	 * @return
	 */
    Page<KidDTO> findPaginated(Integer page, Integer size);
    
    /**
     * 
     * @param pageable
     * @return
     */
    Page<KidDTO> findPaginated(Pageable pageable);
    
    
    /**
     * 
     * @param id
     * @return
     */
    KidDTO getKidById(final String id);
    
    /**
     * 
     * @return
     */
    Long getTotalKids();
    
    /**
     * 
     * @param id
     * @return
     */
    String getProfileImage(ObjectId id);
    
    /**
     * 
     * @param id
     */
    void deleteById(String id);
    
    /**
     * 
     * @param id
     */
    void deleteById(ObjectId id);
    
    
    /**
     * Save Guardian
     * @param guardians
     * @param kid
     * @return
     */
    Iterable<KidGuardianDTO> save(final List<SaveGuardianDTO> guardians, final ObjectId kid);
    
    /**
     * Find Supervised Children Confirmed
     * @param guardian
     * @param kid
     * @return
     */
    Iterable<KidGuardianDTO> findSupervisedChildrenConfirmed(final ObjectId guardian, final ObjectId kid);
    
    /**
     * Find Supervised Children No Confirmed
     * @param guardian
     * @param kid
     * @return
     */
    Iterable<KidGuardianDTO> findSupervisedChildrenNoConfirmed(final ObjectId guardian, final ObjectId kid);
    
    /**
     * Delete Supervised Children No Confirmed
     * @param guardian
     * @param kid
     */
    void deleteSupervisedChildrenNoConfirmed(final ObjectId guardian, final ObjectId kid);
    
    
    /**
     * Find Supervised Children Confirmed
     * @param guardian
     * @return
     */
    Iterable<KidGuardianDTO> findSupervisedChildrenConfirmed(final ObjectId guardian);
    
    /**
     * Find Supervised Children No Confirmed
     * @param guardian
     * @return
     */
    Iterable<KidGuardianDTO> findSupervisedChildrenNoConfirmed(final ObjectId guardian);
    
    
    /**
     * Find Supervised Children No Confirmed By Id
     * @param guardian
     * @return
     */
    KidGuardianDTO findSupervisedChildrenNoConfirmedById(final ObjectId id);
    
    /**
     * Find Supervised Child Confirmed By Id
     * @param guardian
     * @param kid
     * @return
     */
    KidGuardianDTO findSupervisedChildConfirmedById(final ObjectId guardian, final ObjectId kid);
    
    
    /**
     * Delete Supervised Children No Confirmed
     * @param guardian
     */
    void deleteSupervisedChildrenNoConfirmed(final ObjectId guardian);
    
    
    /**
     * Accept Supervised Children No Confirmed
     * @param guardian
     */
    void acceptSupervisedChildrenNoConfirmed(final ObjectId guardian);
    
    
    /**
     * Delete Supervised Children No Confirmed By Id
     * @param guardian
     * @return
     */
    void deleteSupervisedChildrenNoConfirmedById(final ObjectId id);
    
    /**
     * Accept Supervised Children No Confirmed
     * @param id
     */
    void acceptSupervisedChildrenNoConfirmedById(final ObjectId id);
    
    /**
     * Delete Supervised Children Confirmed By ID
     * @param id
     */
    void deleteSupervisedChildrenConfirmedById(final ObjectId id);
    
    /**
     * Find Supervised Children Confirmed By Id
     * @param id
     * @return
     */
    KidGuardianDTO findSupervisedChildrenConfirmedById(final ObjectId id);
    
    /**
     * Get Guardians
     * @param id
     * @return
     */
    Iterable<KidGuardianDTO> getGuardians(final ObjectId id);
    
    /**
     * Get Confirmed Guardians
     * @param id
     * @return
     */
    Iterable<KidGuardianDTO> getConfirmedGuardians(final ObjectId id);
    
    /**
     * Save Current Location
     * @param kid
     * @param location
     * @return
     */
    LocationDTO saveCurrentLocation(final String kid, final SaveLocationDTO location);
    
    /**
     * Get Current Location
     * @param kid
     * @return
     */
    LocationDTO getCurrentLocation(final String kid);
    
    
    /**
     * Upload Kid Profile Image
     * @param kid
     * @param requestUploadFile
     * @return
     */
    ImageDTO uploadKidProfileImage(final ObjectId kid, 
    		final RequestUploadFile requestUploadFile);
}
