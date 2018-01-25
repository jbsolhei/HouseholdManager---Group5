package auth;

/**
 * <p>Authorization types for different user actions.</p>
 * <p>This enum defines different user authorization levels required by different user actions.</p>
 */
public enum AuthType {
    /**
     * Default auth level. Requires only the client to be logged in.
     */
    DEFAULT,

    /**
     * Read access of a user. Requires the client user to be part of the same household
     * as the user it is attempting to read.
     */
    USER_READ,

    /**
     * Modification access of a user. Requires that the client user is the user it is
     * attempting to modify. This mean any logged in user can only modify their own user data.
     */
    USER_MODIFY,

    /**
     * Read and modify access to a chore. Requires that the client user is part of the
     * household which the chore belongs to.
     */
    CHORE,

    /**
     * Read and modify access to a shopping list. Requires that the client user has been
     * given access to the shopping list.
     */
    SHOPPING_LIST,

    /**
     * Access to a household. Requires the client user to ba part of the household in question.
     */
    HOUSEHOLD,

    /**
     * Admin access to a household (ability to remove users from the household etc.).
     * Requires the client user to have the admin flag set to true for the household in question.
     */
    HOUSEHOLD_ADMIN,

    /**
     * Delete access to a notification, which is done when the notification is read by the user.
     * Requires that the client user is the user the notification belongs to.
     */
    NOTIFICATION_DELETE
}
