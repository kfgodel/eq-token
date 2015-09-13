package ar.com.kfgodel.eqtoken.objects;

import ar.com.kfgodel.eqtoken.EqualityToken;
import ar.com.kfgodel.eqtoken.EqualityTokenizable;
import ar.com.kfgodel.eqtoken.impl.MutableToken;
import ar.com.kfgodel.eqtoken.impl.values.ImmutableDiscriminator;
import ar.com.kfgodel.eqtoken.impl.values.MutableDiscriminator;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by tenpines on 12/09/15.
 */
public class MutableComplexObject implements EqualityTokenizable {
    private EqualityToken eqToken;

    public static MutableComplexObject create(TraditionalComplexObject traditional){
        MutableComplexObject object = new MutableComplexObject();
        object.eqToken = MutableToken.create(
                ImmutableDiscriminator.create(traditional.getNumber()),
                MutableDiscriminator.create(traditional::getText),
                ImmutableDiscriminator.create(converted(traditional.getChildren()))
        );
        return object;
    }

    private static List<MutableComplexObject> converted(List<TraditionalComplexObject> children) {
        return children.stream()
                .map(MutableComplexObject::create)
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
