package sanchez.sanchez.sergio.bullkeeper.web.rest.response;

/**
 * 
 * @author sergiosanchezsanchez
 *
 */
public enum GuardianResponseCode implements IResponseCodeTypes {

    ALL_GUARDIANS(400L), 
    SINGLE_GUARDIAN(401L), 
    CHILDREN_OF_GUARDIAN(402L), 
    GUARDIAN_REGISTERED_SUCCESSFULLY(403L), 
    ADDED_KID_TO_GUARDIAN(404L), 
    SELF_GUARDIAN(405L), 
    GUARDIAN_NOT_FOUND(406L),
    ADDED_KID_TO_SELF_GUARDIAN(407L),
    NO_CHILDREN_FOUND_FOR_SELF_GUARDIAN(408L),
    NO_CHILDREN_FOUND_FOR_GUARDIAN(409L), 
    CHILDREN_OF_SELF_GUARDIAN(410L), 
    GUARDIANS_NOT_FOUND(411L),
    GUARDIAN_RESET_PASSWORD_REQUEST(412L), 
    GUARDIAN_UPDATED_SUCCESSFULLY(413L), 
    SELF_GUARDIAN_UPDATED_SUCCESSFULLY(414L),
    AUTHENTICATION_SUCCESS(415L), 
    BAD_CREDENTIALS(416L), 
    ACCOUNT_DISABLED(417L),
    ACCOUNT_LOCKED(418L),
    ACCOUNT_UNLOCKED(419L),
    AUTHENTICATION_VIA_FACEBOOK_SUCCESS(420L),
    GET_INFORMATION_FROM_FACEBOOK_FAILED(421L),
    SUCCESSFUL_ACCOUNT_DELETION_REQUEST(422L),
    INVALID_FACEBOOK_ID(423L), 
    USERNAME_NOT_FOUND(424L), 
    PROFILE_IMAGE_UPLOAD_SUCCESSFULLY(425L),
    PROFILE_IMAGE_DELETED_SUCCESSFULLY(426L),
    SAVE_KID_INFORMATION(427L), 
    ALERTS_FOR_SELF_GUARDIAN(428L),
    ALERTS_OF_SELF_GUARDIAN_DELETED(433L), 
    ACCOUNT_ACTIVATION_EMAIL_SENT(434L), 
    USER_SYSTEM_PREFERENCES_SAVED(435L), 
    USER_SYSTEM_PREFERENCES(436L),
    EMAIL_ALREADY_EXISTS(437L),
    AUTHENTICATION_VIA_GOOGLE_SUCCESS(438L), 
    WARNING_ALERTS_OF_SELF_GUARDIAN_DELETED(439L),
    INFO_ALERTS_OF_SELF_GUARDIAN_DELETED(440L), 
    DANGER_ALERTS_OF_SELF_GUARDIAN_DELETED(441L),
    SUCCESS_ALERTS_OF_SELF_GUARDIAN_DELETED(442L),
    GET_SUPERVISED_CHILDREN_CONFIRMED(443L),
    GET_SUPERVISED_CHILDREN_NO_CONFIRMED(444L),
    DELETE_SUPERVISED_CHILDREN_NO_CONFIRMED(445L),
    ACCEPT_SUPERVISED_CHILDREN_NO_CONFIRMED(446L),
    GET_SUPERVISED_CHILDREN_NO_CONFIRMED_DETAIL(447L),
    DELETE_SUPERVISED_CHILDREN_NO_CONFIRMED_BY_ID(448L),
    ACCEPT_SUPERVISED_CHILDREN_NO_CONFIRMED_BY_ID(449L),
    GET_SUPERVISED_CHILDREN_CONFIRMED_DETAIL(450L),
    DELETE_SUPERVISED_CHILDREN_CONFIRMED_BY_ID(451L),
    NO_SUPERVISED_CHILDREN_CONFIRMED_FOUND(452L),
    NO_SUPERVISED_CHILDREN_NO_CONFIRMED_FOUND(453L),
    SUPERVISED_CHILDREN_CONFIRMED_NOT_FOUND_EXCEPTION(454L),
    SUPERVISED_CHILDREN_NOT_CONFIRMED_NOT_FOUND_EXCEPTION(455L),
    GUARDIANS_RESULT_SEARCH(456L),
    EMAIL_CHANGE_REQUEST_COMPLETED(457L),
    USER_PASSWORD_CHANGED_SUCCESSFULLY(458L);

    private Long code;

    private GuardianResponseCode(Long code) {
        this.code = code;
    }

    @Override
    public Long getResponseCode() {
        return code;
    }

}
