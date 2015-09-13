package ar.com.kfgodel.eqtoken.impl.values;

import java.util.Objects;

/**
 * Created by tenpines on 12/09/15.
 */
public class ImmutableDiscriminator implements Discriminator {

    private Object value;
    private int cachedHashcode;
    private boolean hashcodeNotCalculated;

    public static ImmutableDiscriminator create(Object value){
        ImmutableDiscriminator immutableDiscriminator = new ImmutableDiscriminator();
        immutableDiscriminator.value = value;
        immutableDiscriminator.hashcodeNotCalculated = true;
        return immutableDiscriminator;
    }

    @Override
    public boolean equals(Object obj) {
        return DiscriminatorEquality.areEquals(this, obj);
    }

    @Override
    public int hashCode() {
        if(hashcodeNotCalculated){
            // First and only time we calculate hashcode
            cachedHashcode = Objects.hashCode(value);
            hashcodeNotCalculated = false;
        }
        return cachedHashcode;
    }

    @Override
    public Object getValue() {
        return value;
    }
}
