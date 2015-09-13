package ar.com.kfgodel.eqtoken.objects;

import ar.com.kfgodel.eqtoken.EqualityToken;
import ar.com.kfgodel.eqtoken.EqualityTokenizable;
import ar.com.kfgodel.eqtoken.impl.ImmutableToken;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by tenpines on 12/09/15.
 */
public class ImmutableComplexObject implements EqualityTokenizable {
    private EqualityToken eqToken;

    public static ImmutableComplexObject create(TraditionalComplexObject traditional){
        ImmutableComplexObject object = new ImmutableComplexObject();
        object.eqToken = ImmutableToken.create(traditional.getNumber(), traditional.getText(), converted(traditional.getChildren()));
        return object;
    }

    private static List<ImmutableComplexObject> converted(List<TraditionalComplexObject> children) {
        return children.stream()
                .map(ImmutableComplexObject::create)
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
