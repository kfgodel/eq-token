package ar.com.kfgodel.eqtoken;

/**
 * This type represents an equality token. Used to define the equality and hashcode value of other
 * object (the represented)
 * Created by tenpines on 11/09/15.
 */
public interface EqualityToken {

    /**
     * Compares the passed instance to the represented object using this instance definition of equality
     * @param obj The object to compare to
     * @return true if they are considered equal, false if not
     */
    boolean equals(Object obj);

    /**
     * Calculates the hashcode for this token that is the same for the represented object
     * @return The hashcode for the represented object
     */
    int hashCode();

    /**
     * Returns the array of values that define this token to be used in an equality comparison with another
     * equality token.<br>
     *     Array is used for performance reasons
     * @return The array of values to compare to
     */
    Object[] getDiscriminators();

}
