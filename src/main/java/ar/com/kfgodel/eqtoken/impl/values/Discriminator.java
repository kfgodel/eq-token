package ar.com.kfgodel.eqtoken.impl.values;

/**
 * Created by tenpines on 12/09/15.
 */
public interface Discriminator {

    /**
     * The original value this discriminator is wrapping
     * @return The value to use in comparisons
     */
    Object getValue();
}
