package ar.com.kfgodel.eqtoken.impl;

import ar.com.kfgodel.eqtoken.EqualityToken;

import java.util.Objects;

/**
 * This type represents a token based on immutable values
 * Created by tenpines on 11/09/15.
 */
public class ImmutableToken implements EqualityToken {

    private Object[] values;
    private int cachedTokenHashcode;
    private int[] cachedValueHashcodes;

    @Override
    public boolean equals(Object obj) {
        return TokenEquality.areEquals(this, obj);
    }

    @Override
    public int hashCode() {
        prepareCachedStateIfNeeded();
        return cachedTokenHashcode;
    }

    private void prepareCachedStateIfNeeded() {
        if(cachedValueHashcodes != null){
            // Already prepared
            return;
        }
        cachedTokenHashcode = calculateValueHashcodes();
    }

    private int calculateValueHashcodes() {
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
        prepareCachedStateIfNeeded();
        return cachedValueHashcodes[valueIndex];
    }

    @Override
    public Object getValue(int valueIndex) {
        return values[valueIndex];
    }

    public static ImmutableToken create(Object... values){
        ImmutableToken token = new ImmutableToken();
        token.values = values;
        return token;
    }
}
