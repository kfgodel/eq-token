package ar.com.kfgodel.eqtoken;

import ar.com.kfgodel.eqtoken.impl.ImmutableToken;

/**
 * Created by tenpines on 12/09/15.
 */
public class ImmutableObjectEqToken implements EqualityTokenizable {
    private EqualityToken eqToken;

    public static ImmutableObjectEqToken create(ImmutableObjectTraditionImplementation traditional){
        ImmutableObjectEqToken object = new ImmutableObjectEqToken();
        object.eqToken = ImmutableToken.create(traditional.getNumber(), traditional.getText(), traditional.getChildren());
        return object;
    }

    @Override
    public int hashCode() {
        return eqToken.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return eqToken.equals(obj);
    }


    @Override
    public EqualityToken getToken() {
        return eqToken;
    }
}
