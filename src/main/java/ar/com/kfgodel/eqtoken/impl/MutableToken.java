package ar.com.kfgodel.eqtoken.impl;

import ar.com.kfgodel.eqtoken.EqualityToken;
import ar.com.kfgodel.eqtoken.impl.values.Discriminator;

import java.util.Arrays;

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
    public boolean equals(Object obj) {
        return TokenEquality.areEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(discriminators);
    }
}
