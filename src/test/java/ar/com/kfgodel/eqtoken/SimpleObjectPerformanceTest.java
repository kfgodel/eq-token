package ar.com.kfgodel.eqtoken;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.kfgodel.eqtoken.objects.ImmutableSimpleObject;
import ar.com.kfgodel.eqtoken.objects.MutableComplexObject;
import ar.com.kfgodel.eqtoken.objects.TraditionalComplexObject;
import ar.com.kfgodel.eqtoken.objects.TraditionalSimpleObject;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by tenpines on 12/09/15.
 */
@RunWith(JavaSpecRunner.class)
public class SimpleObjectPerformanceTest extends JavaSpec<EqTokenTestContext> {

    public static final int RUN_TIMES = 1_000_000;

    @Override
    public void define() {

        describe("an immutable simple object", ()->{

            it("should produce same hashcode for eqtoken and traditional approach", () -> {
                int traditionalHashcode = TraditionalSimpleObject.create().hashCode();
                int eqHashcode = ImmutableSimpleObject.create(TraditionalSimpleObject.create()).hashCode();

                assertThat(traditionalHashcode).isEqualTo(eqHashcode);
            });

            it("should produce a faster hashcode with eq-token than traditional approach", () -> {
                MicroBenchmark bench = MicroBenchmark.create();

                double traditionalTimeMillis = bench.measure("Immutable hashcode Traditional", SimpleObjectPerformanceTest::runTraditionalHashcodeManyTimes);
                double eqtokenTimeMillis = bench.measure("Immutable hashcode EqToken", SimpleObjectPerformanceTest::runEqTokenHashcodeManyTimes);

                bench.printResults();

                assertThat(eqtokenTimeMillis).isLessThan(traditionalTimeMillis * 0.1);
            });

            it("should run somehow slower equals for equals objects with eq-token implementation", () -> {
                MicroBenchmark bench = MicroBenchmark.create();

                double traditionalTimeMillis = bench.measure("Immutable equals Traditional (=)", SimpleObjectPerformanceTest::runTraditionalEqualsOnEqualObjectsManyTimes);
                double eqtokenTimeMillis = bench.measure("Immutable equals EqToken (=)", SimpleObjectPerformanceTest::runEqTokenEqualsOnEqualObjectsManyTimes);

                bench.printResults();

                assertThat(eqtokenTimeMillis).isLessThan(traditionalTimeMillis * 0.9);
            });

            it("should run faster equals for different objects with eq-token implementation", () -> {
                MicroBenchmark bench = MicroBenchmark.create();

                double traditionalTimeMillis = bench.measure("Immutable equals Traditional (!=)", SimpleObjectPerformanceTest::runTraditionalEqualsOnDifferentObjectsManyTimes);
                double eqtokenTimeMillis = bench.measure("1 Immutable equals EqToken (!=)", SimpleObjectPerformanceTest::runEqTokenEqualsOnDifferentObjectsManyTimes);

                bench.printResults();

                assertThat(eqtokenTimeMillis).isLessThan(traditionalTimeMillis * 0.9);
            });

        });

        xdescribe("a mutable complex object", () -> {
            it("should produce same hashcode for eqtoken and traditional approach", () -> {
                int traditionalHashcode = TraditionalComplexObject.create().hashCode();
                int eqHashcode = MutableComplexObject.create(TraditionalComplexObject.create()).hashCode();

                assertThat(traditionalHashcode).isEqualTo(eqHashcode);
            });

            it("should produce a faster hashcode with eq-token than traditional approach", () -> {
                MicroBenchmark bench = MicroBenchmark.create();

                double traditionalTimeMillis = bench.measure("Mutable hashcode Traditional", SimpleObjectPerformanceTest::runTraditionalHashcodeManyTimes);
                double eqtokenTimeMillis = bench.measure("Mutable hashcode EqToken", SimpleObjectPerformanceTest::runMutableEqTokenHashcodeManyTimes);

                bench.printResults();

                assertThat(eqtokenTimeMillis).isLessThan(traditionalTimeMillis * 0.02);
            });

            it("should run slower equals for equals objects with eq-token implementation", () -> {
                MicroBenchmark bench = MicroBenchmark.create();

                double traditionalTimeMillis = bench.measure("Mutable equals Traditional (=)", SimpleObjectPerformanceTest::runTraditionalEqualsOnEqualObjectsManyTimes);
                double eqtokenTimeMillis = bench.measure("Mutable equals EqToken (=)", SimpleObjectPerformanceTest::runMutableEqTokenEqualsOnEqualObjectsManyTimes);

                bench.printResults();

                assertThat(eqtokenTimeMillis).isLessThan(traditionalTimeMillis * 4);
            });

            it("should run faster equals for different objects with eq-token implementation", () -> {
                MicroBenchmark bench = MicroBenchmark.create();

                double traditionalTimeMillis = bench.measure("Mutable equals Traditional (!=)", SimpleObjectPerformanceTest::runTraditionalEqualsOnDifferentObjectsManyTimes);
                double eqtokenTimeMillis = bench.measure("Mutable equals EqToken (!=)", SimpleObjectPerformanceTest::runMutableEqTokenEqualsOnDifferentObjectsManyTimes);

                bench.printResults();

                assertThat(eqtokenTimeMillis).isLessThan(traditionalTimeMillis * 0.4);
            });
        });

    }

    public static void runTraditionalHashcodeManyTimes(){
        Object object = TraditionalSimpleObject.create();
        for (int i = 0; i < RUN_TIMES; i++) {
            object.hashCode();
        }

    }

    public static void runEqTokenHashcodeManyTimes(){
        Object object = ImmutableSimpleObject.create(TraditionalSimpleObject.create());
        for (int i = 0; i < RUN_TIMES; i++) {
            object.hashCode();
        }
    }

    public static void runMutableEqTokenHashcodeManyTimes(){
        Object object = MutableComplexObject.create(TraditionalComplexObject.create());
        for (int i = 0; i < RUN_TIMES; i++) {
            object.hashCode();
        }
    }

    public static void runTraditionalEqualsOnEqualObjectsManyTimes(){
        Object oneObject = TraditionalSimpleObject.create();
        Object otherObject = TraditionalSimpleObject.create();
        for (int i = 0; i < RUN_TIMES; i++) {
            oneObject.equals(otherObject);
        }

    }

    public static void runEqTokenEqualsOnEqualObjectsManyTimes(){
        Object oneObject = ImmutableSimpleObject.create(TraditionalSimpleObject.create());
        Object otherObject = ImmutableSimpleObject.create(TraditionalSimpleObject.create());
        for (int i = 0; i < RUN_TIMES; i++) {
            oneObject.equals(otherObject);
        }
    }

    public static void runMutableEqTokenEqualsOnEqualObjectsManyTimes(){
        Object oneObject = MutableComplexObject.create(TraditionalComplexObject.create());
        Object otherObject = MutableComplexObject.create(TraditionalComplexObject.create());
        for (int i = 0; i < RUN_TIMES; i++) {
            oneObject.equals(otherObject);
        }
    }


    public static void runTraditionalEqualsOnDifferentObjectsManyTimes(){
        Object oneObject = TraditionalSimpleObject.create();
        TraditionalSimpleObject otherObject = TraditionalSimpleObject.create();
        otherObject.setText("Different");
        for (int i = 0; i < RUN_TIMES; i++) {
            oneObject.equals(otherObject);
        }

    }

    public static void runEqTokenEqualsOnDifferentObjectsManyTimes(){
        Object oneObject = ImmutableSimpleObject.create(TraditionalSimpleObject.create());

        TraditionalSimpleObject different = TraditionalSimpleObject.create();
        different.setText("Different");
        // Because it's immutable we need to change the text before creating the instance
        Object otherObject = ImmutableSimpleObject.create(different);

        for (int i = 0; i < RUN_TIMES; i++) {
            oneObject.equals(otherObject);
        }

    }

    public static void runMutableEqTokenEqualsOnDifferentObjectsManyTimes(){
        Object oneObject = MutableComplexObject.create(TraditionalComplexObject.create());

        TraditionalComplexObject different = TraditionalComplexObject.create();
        Object otherObject = MutableComplexObject.create(different);
        // Because it's mutable we can change the text after creating the instance
        different.getChildren().get(0).setText("Different");

        for (int i = 0; i < RUN_TIMES; i++) {
            oneObject.equals(otherObject);
        }

    }

}
