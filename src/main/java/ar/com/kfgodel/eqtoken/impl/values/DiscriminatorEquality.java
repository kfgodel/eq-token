package ar.com.kfgodel.eqtoken.impl.values;

import java.util.Objects;

/**
 * This type captures the definition of equality for discriminator values
 * Created by tenpines on 12/09/15.
 */
public class DiscriminatorEquality {

    public static boolean areEquals(Discriminator firstDiscriminator, Object obj) {
        if(firstDiscriminator == obj){
            return true;
        }
        if(!Discriminator.class.isInstance(obj)){
            return false;
        }
        Discriminator secondDiscriminator = Discriminator.class.cast(obj);
        return Objects.equals(firstDiscriminator.getValue(), secondDiscriminator.getValue());
    }
}
