package ar.com.kfgodel.eqtoken.impl.values;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Created by tenpines on 12/09/15.
 */
public class MutableDiscriminator implements Discriminator {

    private Object value;
    private Supplier<?> valueSupplier;

    public static MutableDiscriminator create(Object value){
        MutableDiscriminator mutableDiscriminator = new MutableDiscriminator();
        mutableDiscriminator.value = value;
        return mutableDiscriminator;
    }
    
    
    public static MutableDiscriminator create(Supplier<?> valueSupplier){
        MutableDiscriminator mutableDiscriminator = new MutableDiscriminator();
        mutableDiscriminator.valueSupplier = valueSupplier;
        return mutableDiscriminator;
    }

    @Override
    public Object getValue() {
        if(valueSupplier == null){
            return value;
        }
        return valueSupplier.get();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getValue());
    }

    @Override
    public boolean equals(Object obj) {
        return DiscriminatorEquality.areEquals(this, obj);
    }
}
