package ar.com.kfgodel.eqtoken;

/**
 * This type represents contract for objects that use the EqualityToken for their equality definition
 * Created by tenpines on 11/09/15.
 */
public interface EqualityTokenizable {

    /**
     * Returns the equality token used for comparison with other EqualityTokenizable instances
     * @return The token to be used in comparisons
     */
    EqualityToken getToken();
}
