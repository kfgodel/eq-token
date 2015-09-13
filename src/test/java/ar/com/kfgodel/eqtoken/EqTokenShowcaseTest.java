package ar.com.kfgodel.eqtoken;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.kfgodel.eqtoken.impl.ImmutableToken;
import ar.com.kfgodel.eqtoken.impl.MutableToken;
import ar.com.kfgodel.eqtoken.impl.values.ImmutableDiscriminator;
import ar.com.kfgodel.eqtoken.impl.values.MutableDiscriminator;
import ar.com.kfgodel.eqtoken.objects.TestObject;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * This class shows and tests general eq token use cases
 * Created by tenpines on 11/09/15.
 */
@RunWith(JavaSpecRunner.class)
public class EqTokenShowcaseTest extends JavaSpec<EqTokenTestContext>{
    @Override
    public void define() {
        describe("an equality token", ()->{

            context().firstValue(TestObject::create);
            context().secondValue(TestObject::create);

            describe("when based on immutable values", ()->{


                it("is created using the immutable token", () -> {
                    ImmutableToken token = ImmutableToken.create(context().firstValue(), context().secondValue());

                    assertThat(token).isNotNull();
                });

                describe("once created", ()->{

                    context().token(() -> ImmutableToken.create(context().firstValue(), context().secondValue()));

                    it("generates a single hashcode combining discriminator hashcodes (as arrays or collections do)", () -> {
                        context().firstValue().answerAsHashcode(1);
                        context().secondValue().answerAsHashcode(2);

                        int tokenHashcode = context().token().hashCode();

                        // Based on collection definition for combined hashcode
                        int hashcodeForArrayOf1And2 = Arrays.hashCode(new int[]{1, 2});
                        assertThat(tokenHashcode).isEqualTo(hashcodeForArrayOf1And2);
                    });

                    it("caches the hashcode, so discrminator hashcodes are called only once", () -> {

                        int firstResult = context().token().hashCode();
                        int secondResult = context().token().hashCode();

                        assertThat(firstResult).isEqualTo(secondResult);

                        assertThat(context().firstValue().numberOfHashcodeCalls()).isEqualTo(1);
                        assertThat(context().secondValue().numberOfHashcodeCalls()).isEqualTo(1);
                    });

                    it("is equal to itself", () -> {
                        boolean result = context().token().equals(context().token());

                        assertThat(result).isTrue();
                    });

                    it("is equal to another token with same discriminators", ()->{
                        ImmutableToken identicalToken = ImmutableToken.create(context().firstValue(), context().secondValue());

                        boolean result = context().token().equals(identicalToken);

                        assertThat(result).isTrue();
                    });

                    it("is different if different amount of discriminators", ()->{
                        Integer thirdDiscriminator = 3;
                        ImmutableToken differentToken = ImmutableToken.create(context().firstValue(), context().secondValue(), thirdDiscriminator);

                        boolean result = context().token().equals(differentToken);

                        assertThat(result).isFalse();
                    });

                    it("is equal to another EqualityTokenizable object that has equal token", ()->{
                        EqualityTokenizable tokenizable = Mockito.mock(EqualityTokenizable.class);
                        when(tokenizable.getToken()).thenReturn(context().token());

                        boolean result = context().token().equals(tokenizable);

                        assertThat(result).isTrue();
                    });

                });

            });

            describe("when based on mutable values", ()->{

                it("is created using the mutable token with at least one mutable value", ()->{
                    MutableToken token = MutableToken.create(MutableDiscriminator.create(context().firstValue()));

                    assertThat(token).isNotNull();
                });

                it("can use immutable values to indicate those that don't change", ()->{
                    MutableToken token = MutableToken.create(
                            MutableDiscriminator.create(context().firstValue()),
                            ImmutableDiscriminator.create(context().secondValue())
                    );

                    assertThat(token).isNotNull();
                });

                describe("once created", () -> {

                    context().token(() -> MutableToken.create(
                            MutableDiscriminator.create(context().firstValue()),
                            ImmutableDiscriminator.create(context().secondValue())
                    ));

                    it("generates a single hashcode combining discriminator hashcodes (as arrays or collections do)", () -> {
                        context().firstValue().answerAsHashcode(1);
                        context().secondValue().answerAsHashcode(2);

                        int tokenHashcode = context().token().hashCode();

                        // Based on collection definition for combined hashcode
                        int hashcodeForArrayOf1And2 = Arrays.hashCode(new int[]{1, 2});
                        assertThat(tokenHashcode).isEqualTo(hashcodeForArrayOf1And2);
                    });

                    it("caches immutable values hashcodes, asks for mutable hashcodes each time", () -> {

                        int firstResult = context().token().hashCode();
                        int secondResult = context().token().hashCode();

                        assertThat(firstResult).isEqualTo(secondResult);

                        assertThat(context().firstValue().numberOfHashcodeCalls()).isEqualTo(2);
                        assertThat(context().secondValue().numberOfHashcodeCalls()).isEqualTo(1);
                    });

                    it("can change its hashcode over time", () -> { // Be careful with hash collections
                        context().firstValue().answerAsHashcode(1, 3);
                        context().secondValue().answerAsHashcode(2);

                        int firstHashcode = context().token().hashCode();
                        int secondHashcode = context().token().hashCode();

                        assertThat(firstHashcode).isEqualTo(Arrays.hashCode(new int[]{1, 2}));
                        assertThat(secondHashcode).isEqualTo(Arrays.hashCode(new int[]{3, 2}));
                    });

                    it("is equal to itself", () -> {
                        boolean result = context().token().equals(context().token());

                        assertThat(result).isTrue();
                    });

                    it("is equal to another token with same discriminators", ()->{
                        ImmutableToken identicalToken = ImmutableToken.create(context().firstValue(), context().secondValue());

                        boolean result = context().token().equals(identicalToken);

                        assertThat(result).isTrue();
                    });

                    it("is different if different amount of discriminators", ()->{
                        Integer thirdDiscriminator = 3;
                        ImmutableToken differentToken = ImmutableToken.create(context().firstValue(), context().secondValue(), thirdDiscriminator);

                        boolean result = context().token().equals(differentToken);

                        assertThat(result).isFalse();
                    });

                    it("is equal to another EqualityTokenizable object that has equal token", ()->{
                        EqualityTokenizable tokenizable = Mockito.mock(EqualityTokenizable.class);
                        when(tokenizable.getToken()).thenReturn(context().token());

                        boolean result = context().token().equals(tokenizable);

                        assertThat(result).isTrue();
                    });


                });
            });
        });
    }
}
