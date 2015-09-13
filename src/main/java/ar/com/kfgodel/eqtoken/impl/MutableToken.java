package ar.com.kfgodel.eqtoken.impl;

import ar.com.kfgodel.eqtoken.EqualityToken;
import ar.com.kfgodel.eqtoken.impl.values.Discriminator;

import java.util.Arrays;
import java.util.Objects;

/**
 * This type represents an equality token with at least one changing value
 * Created by tenpines on 12/09/15.
 */
public class MutableToken implements EqualityToken {

    private Discriminator[] discriminators;

    public static MutableToken create(Discriminator... values){
        MutableToken token = new MutableToken();
        token.discriminators = values;
        return token;
    }

    @Override
    public Object[] getValues() {
        Object[] values = new Object[discriminators.length];
        for (int i = 0; i < discriminators.length; i++) {
            Discriminator discriminator = discriminators[i];
            values[i] = discriminator.getValue();
        }
        return values;
    }

    @Override
    public int valueCount() {
        return discriminators.length;
    }

    @Override
    public int getHashOfValue(int valueIndex) {
        return Objects.hashCode(discriminators[valueIndex]);
    }

    @Override
    public Object getValue(int valueIndex) {
        return discriminators[valueIndex].getValue();
    }

    @Override
    public boolean equals(Object obj) {
        return TokenEquality.areEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(discriminators);
    }
}
