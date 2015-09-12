package ar.com.kfgodel.eqtoken.impl;

import ar.com.kfgodel.eqtoken.EqualityToken;
import ar.com.kfgodel.eqtoken.impl.values.Discriminator;

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
    public Object[] getDiscriminators() {
        return discriminators;
    }

    @Override
    public boolean equals(Object obj) {
        return TokenEquality.areEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return TokenEquality.calculateHashcodeFor(this);
    }
}
