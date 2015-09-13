package ar.com.kfgodel.eqtoken;

import ar.com.kfgodel.eqtoken.impl.ImmutableToken;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by tenpines on 12/09/15.
 */
public class ImmutableObjectEqTokenImplementation implements EqualityTokenizable {
    private EqualityToken eqToken;

    public static ImmutableObjectEqTokenImplementation create(EqualityTraditionalImplementation traditional){
        ImmutableObjectEqTokenImplementation object = new ImmutableObjectEqTokenImplementation();
        object.eqToken = ImmutableToken.create(traditional.getNumber(), traditional.getText(), converted(traditional.getChildren()));
        return object;
    }

    private static List<ImmutableObjectEqTokenImplementation> converted(List<EqualityTraditionalImplementation> children) {
        return children.stream()
                .map(ImmutableObjectEqTokenImplementation::create)
                .collect(Collectors.toList());
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
