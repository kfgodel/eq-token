package ar.com.kfgodel.eqtoken.impl;

import ar.com.kfgodel.eqtoken.EqualityToken;
import ar.com.kfgodel.eqtoken.EqualityTokenizable;

import java.util.Arrays;
import java.util.Objects;

/**
 * This type captures the equality criteria used on equality tokens
 * Created by tenpines on 12/09/15.
 */
public class TokenEquality {

    public static boolean areEquals(Object first, Object second){
        if(first == second){
            return true;
        }
        if(EqualityToken.class.isInstance(first)){
            return areEquals(EqualityToken.class.cast(first), second);
        } else if (EqualityTokenizable.class.isInstance(first)){
            EqualityTokenizable firstTokenizable = EqualityTokenizable.class.cast(first);
            return areEquals(firstTokenizable.getToken(), second);
        } else if(EqualityToken.class.isInstance(second) || EqualityTokenizable.class.isInstance(second)){
            return false;
        }
        return Objects.equals(first, second);
    }


    /**
     * Compares a token to another object and determines if they are considered equals.
     * Two token will be equal id they are the same instance or they are defined on equal values
     * @param firstToken The known token
     * @param obj The object to compare to
     * @return False if they are considered different
     */
    public static boolean areEquals(EqualityToken firstToken, Object obj) {
        if(firstToken == obj){
            return true;
        }
        EqualityToken secondToken;
        if(EqualityTokenizable.class.isInstance(obj)){
            EqualityTokenizable tokenizable = EqualityTokenizable.class.cast(obj);
            secondToken = tokenizable.getToken();
        } else if (EqualityToken.class.isInstance(obj)){
            secondToken = EqualityToken.class.cast(obj);
        }else{
            return false;
        }
        return areEquals(firstToken, secondToken);
    }

    public static boolean areEquals(EqualityToken firstToken, EqualityToken secondToken) {
        if(firstToken == secondToken){
            return true;
        }
        if(firstToken.hashCode() != secondToken.hashCode()){
            return false;
        }
        int valueCount = firstToken.valueCount();
        if(valueCount != secondToken.valueCount()){
            // Different token size
            return false;
        }
        for (int i = 0; i < valueCount; i++) {
            int firstHash = firstToken.getHashOfValue(i);
            int secondHash = secondToken.getHashOfValue(i);
            if(firstHash != secondHash){
                return false;
            }
        }
        for (int i = 0; i < valueCount; i++) {
            Object firstValue = firstToken.getValue(i);
            Object secondValue = secondToken.getValue(i);
            if (!areEquals(firstValue, secondValue)) {
                return false;
            }
        }
        return true;
    }

    public static int combineHashcodes(int[] cachedValueHashcodes) {
        int result = 1;

        for (int valueHashcode : cachedValueHashcodes)
            result = 31 * result + valueHashcode;

        return result;
    }



}
