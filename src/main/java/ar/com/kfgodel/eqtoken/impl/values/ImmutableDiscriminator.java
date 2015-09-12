package ar.com.kfgodel.eqtoken.impl.values;

/**
 * Created by tenpines on 12/09/15.
 */
public class ImmutableDiscriminator implements Discriminator {

    /**
     * Value used to lazily calculate first hashcode.
     * (Collisions with null hashcode so exception is needed)
     */
    public static final int UNCALCULATED_HASHCODE = 0;

    private Object value;
    private int cachedHashcode;

    public static ImmutableDiscriminator create(Object value){
        ImmutableDiscriminator immutableDiscriminator = new ImmutableDiscriminator();
        immutableDiscriminator.value = value;
        immutableDiscriminator.cachedHashcode = UNCALCULATED_HASHCODE;
        return immutableDiscriminator;
    }

    @Override
    public boolean equals(Object obj) {
        return DiscriminatorEquality.areEquals(this, obj);
    }

    @Override
    public int hashCode() {
        if(value != null && cachedHashcode == UNCALCULATED_HASHCODE){
            // First and only time we calculate hashcode
            cachedHashcode = DiscriminatorEquality.calculateHashcodeFor(this);
        }
        return cachedHashcode;
    }

    @Override
    public Object getValue() {
        return value;
    }
}
