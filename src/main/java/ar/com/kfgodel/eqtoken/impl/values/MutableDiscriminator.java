package ar.com.kfgodel.eqtoken.impl.values;

/**
 * Created by tenpines on 12/09/15.
 */
public class MutableDiscriminator implements Discriminator {

    private Object value;

    public static MutableDiscriminator create(Object value){
        MutableDiscriminator mutableDiscriminator = new MutableDiscriminator();
        mutableDiscriminator.value = value;
        return mutableDiscriminator;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return DiscriminatorEquality.calculateHashcodeFor(this);
    }

    @Override
    public boolean equals(Object obj) {
        return DiscriminatorEquality.areEquals(this, obj);
    }
}
