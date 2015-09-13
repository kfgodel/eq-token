package ar.com.kfgodel.eqtoken.impl;

import ar.com.kfgodel.eqtoken.EqualityToken;

import java.util.Arrays;
import java.util.Objects;

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
    private int[] cachedValueHashcodes;

    @Override
    public boolean equals(Object obj) {
        return TokenEquality.areEquals(this, obj);
    }

    @Override
    public int hashCode() {
        if(cachedHashcode == UNCALCULATED_HASHCODE){
            // First and only time we calculate hashcode
            cachedHashcode = calculateHashes();
        }
        return cachedHashcode;
    }

    private int calculateHashes() {
        cachedValueHashcodes = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            Object value = values[i];
            cachedValueHashcodes[i] = Objects.hashCode(value);
        }

        return TokenEquality.combineHashcodes(cachedValueHashcodes);
    }

    @Override
    public Object[] getValues() {
        return values;
    }

    @Override
    public int valueCount() {
        return values.length;
    }

    @Override
    public int getHashOfValue(int valueIndex) {
        if(cachedValueHashcodes == null){
            hashCode();
        }
        return cachedValueHashcodes[valueIndex];
    }

    @Override
    public Object getValue(int valueIndex) {
        return values[valueIndex];
    }

    public static ImmutableToken create(Object... values){
        ImmutableToken token = new ImmutableToken();
        token.values = values;
        token.cachedHashcode = UNCALCULATED_HASHCODE;
        return token;
    }
}
