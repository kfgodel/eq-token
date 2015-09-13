package ar.com.kfgodel.eqtoken.objects;

import ar.com.kfgodel.eqtoken.EqualityToken;
import ar.com.kfgodel.eqtoken.EqualityTokenizable;
import ar.com.kfgodel.eqtoken.impl.ImmutableToken;

/**
 * Created by tenpines on 13/09/15.
 */
public class ImmutableSimpleObject implements EqualityTokenizable {

    private EqualityToken token;
    
    public static ImmutableSimpleObject create(TraditionalSimpleObject reference){
        ImmutableSimpleObject object = new ImmutableSimpleObject();
        object.token = ImmutableToken.create(reference.getNumber(), reference.getText(), reference.getList());
        return object;
    }

    @Override
    public EqualityToken getToken() {
        return token;
    }

    @Override
    public int hashCode() {
        return token.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return token.equals(obj);
    }
}
