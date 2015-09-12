package ar.com.kfgodel.eqtoken.impl;

import ar.com.kfgodel.eqtoken.EqualityToken;

import java.util.Arrays;

/**
 * This type represents a token based on immutable values
 * Created by tenpines on 11/09/15.
 */
public class ImmutableToken implements EqualityToken {

    /**
     * Value used initially to avoid calculating the discriminators hashcode eagerly.
     */
    public static final int UNCALCULATED_HASHCODE = 0;

    private int cachedHashcode;
    private Object[] values;

    @Override
    public boolean equals(Object obj) {
        return TokenEquality.areEquals(this, obj);
    }

    @Override
    public int hashCode() {
        if(cachedHashcode == UNCALCULATED_HASHCODE){
            // First and only time we calculate hashcode
            cachedHashcode = Arrays.hashCode(values);
        }
        return cachedHashcode;
    }

    @Override
    public Object[] getValues() {
        return values;
    }

    public static ImmutableToken create(Object... values){
        ImmutableToken token = new ImmutableToken();
        token.values = values;
        token.cachedHashcode = UNCALCULATED_HASHCODE;
        return token;
    }
}
