package sanchez.sanchez.sergio.bullkeeper.domain.service;

import org.bson.types.ObjectId;

import sanchez.sanchez.sergio.bullkeeper.web.security.userdetails.CommonUserDetailsAware;

/**
 * Authorization Service
 * @author sergiosanchezsanchez
 *
 */
public interface IAuthorizationService {

	/**
	 * Has Admin Role
	 * @return
	 */
    Boolean hasAdminRole();

    /**
     * Has Parent Role
     * @return
     */
    Boolean hasGuardianRole();

    /**
     * Has Change Password Privilege
     * @return
     */
    Boolean hasChangePasswordPrivilege();

    /**
     * Is Your Son
     * @param id
     * @return
     */
    Boolean isYourGuardian(final String id);
    
    /**
     * Is Your Son
     * @param id
     * @param confirmationRequired
     * @return
     */
    Boolean isYourGuardian(final String id, final Boolean confirmationRequired);
    
    /**
     * Is Your Guardian And Has Admin Permission
     * @param id
     * @return
     */
    Boolean isYourGuardianAndHasAdminPermission(final String id);
    
    
    /**
     * Is Your Son
     * @param guardian
     * @param kid
     * @return
     */
    Boolean isYourGuardian(final String guardian, final String kid);
    
    /**
     * Is Your Guardian and Cad edit parental control rules
     * @param id
     * @return
     */
    Boolean isYourGuardianAndCanEditParentalControlRules(final String id);
    
    
    /**
     * Is Your GUardian And can edit kid information
     * @param id
     * @return
     */
    Boolean isYourGuardianAndCanEditKidInformation(final String id);
    

    /**
     * Get User Details
     * @return
     */
    CommonUserDetailsAware<ObjectId> getUserDetails();
    
    /**
     * Get Current User Id
     * @return
     */
    ObjectId getCurrentUserId();

    /**
     * Is The Authenticated User
     * @param id
     * @return
     */
    Boolean isTheAuthenticatedUser(final String id);

    /**
     * Grant Change Password Privilege
     * @param id
     */
    void grantChangePasswordPrivilege(final String id);
    
    /**
     * Is Your Profile Image
     * @param id
     * @return
     */
    Boolean isYourProfileImage(final String id);
    
    /**
     * It is a profile image of your child
     * @param id
     * @return
     */
    Boolean itIsAProfileImageOfSupervisedKid(final String id);
    
    /**
     * Is Your Profile Public
     * @param id
     * @return
     */
    Boolean isYourProfilePublic(final String id);
    
    /**
     * 
     * @param imageId
     * @return
     */
    Boolean isProfileImageOfPublicAccount(final String imageId);
    
    /**
     * Is Member of the conversation
     * @param id
     * @return
     */
    Boolean isMemberOfTheConversation(final String id);
    
    /**
     * Check If They can talk
     * @param memberOne
     * @param memberTwo
     * @return
     */
    Boolean checkIfTheyCanTalk(final String memberOne, final String memberTwo);
    
}
