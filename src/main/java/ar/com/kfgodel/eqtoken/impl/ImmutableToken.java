package ar.com.kfgodel.eqtoken.impl;

import ar.com.kfgodel.eqtoken.EqualityToken;
import ar.com.kfgodel.eqtoken.EqualityTokenizable;

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
        if(this == obj){
            return true;
        }
        EqualityToken that;
        if(obj instanceof EqualityTokenizable){
            EqualityTokenizable tokenizable = (EqualityTokenizable) obj;
            that = tokenizable.getToken();
        } else if(obj instanceof EqualityToken){
            that = (EqualityToken) obj;
        } else {
            return false;
        }
        if(this.hashCode() != that.hashCode()){
            return false;
        }
        int valueCount = this.values.length;
        if(valueCount != that.valueCount()){
            // Different token size
            return false;
        }
        Object[] thatValues = that.getValues();
        for (int i = 0; i < valueCount; i++) {
            Object firstValue = this.values[i];
            Object secondValue = thatValues[i];
            if (!Objects.equals(firstValue, secondValue)) {
                return false;
            }
        }
        return true;
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
