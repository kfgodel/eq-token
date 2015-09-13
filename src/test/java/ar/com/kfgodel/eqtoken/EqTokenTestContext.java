package ar.com.kfgodel.eqtoken;

import ar.com.dgarcia.javaspec.api.TestContext;
import ar.com.kfgodel.eqtoken.objects.TestObject;

import java.util.function.Supplier;

/**
 * This type allows definition of custom context for eq specs
 * Created by tenpines on 11/09/15.
 */
public interface EqTokenTestContext extends TestContext {

    EqualityToken token();
    void token(Supplier<EqualityToken> definition);

    TestObject firstValue();
    void firstValue(Supplier<TestObject> definition);

    TestObject secondValue();
    void secondValue(Supplier<TestObject> definition);

}
