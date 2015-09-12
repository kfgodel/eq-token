package ar.com.kfgodel.eqtoken.impl;

import ar.com.kfgodel.eqtoken.EqualityToken;
import ar.com.kfgodel.eqtoken.EqualityTokenizable;

import java.util.Arrays;

/**
 * This type captures the equality criteria used on equality tokens
 * Created by tenpines on 12/09/15.
 */
public class TokenEquality {

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
        return Arrays.equals(firstToken.getValues(), secondToken.getValues());
    }

}
