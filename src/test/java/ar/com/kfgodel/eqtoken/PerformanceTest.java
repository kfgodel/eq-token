package ar.com.kfgodel.eqtoken;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by tenpines on 12/09/15.
 */
@RunWith(JavaSpecRunner.class)
public class PerformanceTest extends JavaSpec<EqTokenTestContext> {

    public static final int RUN_TIMES = 1_000_000;

    @Override
    public void define() {

        xdescribe("an immutable complex object", ()->{

            it("should produce same hashcode for eqtoken and traditional approach", () -> {
                int traditionalHashcode = EqualityTraditionalImplementation.create().hashCode();
                int eqHashcode = ImmutableObjectEqTokenImplementation.create(EqualityTraditionalImplementation.create()).hashCode();

                assertThat(traditionalHashcode).isEqualTo(eqHashcode);
            });

            it("should produce a faster hashcode with eq-token than traditional approach", () -> {
                MicroBenchmark bench = MicroBenchmark.create();

                double traditionalTimeMillis = bench.measure("Immutable hashcode Traditional", PerformanceTest::runTraditionalHashcodeManyTimes);
                double eqtokenTimeMillis = bench.measure("Immutable hashcode EqToken", PerformanceTest::runEqTokenHashcodeManyTimes);

                bench.printResults();

                assertThat(eqtokenTimeMillis).isLessThan(traditionalTimeMillis * 0.01);
            });

            it("should run somehow slower equals for equals objects with eq-token implementation", () -> {
                MicroBenchmark bench = MicroBenchmark.create();

                double traditionalTimeMillis = bench.measure("Immutable equals Traditional (=)", PerformanceTest::runTraditionalEqualsOnEqualObjectsManyTimes);
                double eqtokenTimeMillis = bench.measure("Immutable equals EqToken (=)", PerformanceTest::runEqTokenEqualsOnEqualObjectsManyTimes);

                bench.printResults();

                assertThat(eqtokenTimeMillis).isLessThan(traditionalTimeMillis * 1.7);
            });

            it("should run faster equals for different objects with eq-token implementation", () -> {
                MicroBenchmark bench = MicroBenchmark.create();

                double traditionalTimeMillis = bench.measure("Immutable equals Traditional (!=)", PerformanceTest::runTraditionalEqualsOnDifferentObjectsManyTimes);
                double eqtokenTimeMillis = bench.measure("Immutable equals EqToken (!=)", PerformanceTest::runEqTokenEqualsOnDifferentObjectsManyTimes);

                bench.printResults();

                assertThat(eqtokenTimeMillis).isLessThan(traditionalTimeMillis * 0.07);
            });

        });

        describe("a mutable complex object", ()->{
            it("should produce same hashcode for eqtoken and traditional approach", () -> {
                int traditionalHashcode = EqualityTraditionalImplementation.create().hashCode();
                int eqHashcode = MutableObjectEqTokenImplementation.create(EqualityTraditionalImplementation.create()).hashCode();

                assertThat(traditionalHashcode).isEqualTo(eqHashcode);
            });

            it("should produce a faster hashcode with eq-token than traditional approach", () -> {
                MicroBenchmark bench = MicroBenchmark.create();

                double traditionalTimeMillis = bench.measure("Mutable hashcode Traditional", PerformanceTest::runTraditionalHashcodeManyTimes);
                double eqtokenTimeMillis = bench.measure("Mutable hashcode EqToken", PerformanceTest::runMutableEqTokenHashcodeManyTimes);

                bench.printResults();

                assertThat(eqtokenTimeMillis).isLessThan(traditionalTimeMillis * 0.02);
            });

            it("should run slower equals for equals objects with eq-token implementation", () -> {
                MicroBenchmark bench = MicroBenchmark.create();

                double traditionalTimeMillis = bench.measure("Mutable equals Traditional (=)", PerformanceTest::runTraditionalEqualsOnEqualObjectsManyTimes);
                double eqtokenTimeMillis = bench.measure("Mutable equals EqToken (=)", PerformanceTest::runMutableEqTokenEqualsOnEqualObjectsManyTimes);

                bench.printResults();

                assertThat(eqtokenTimeMillis).isLessThan(traditionalTimeMillis * 4);
            });

            it("should run faster equals for different objects with eq-token implementation", () -> {
                MicroBenchmark bench = MicroBenchmark.create();

                double traditionalTimeMillis = bench.measure("Mutable equals Traditional (!=)", PerformanceTest::runTraditionalEqualsOnDifferentObjectsManyTimes);
                double eqtokenTimeMillis = bench.measure("Mutable equals EqToken (!=)", PerformanceTest::runMutableEqTokenEqualsOnDifferentObjectsManyTimes);

                bench.printResults();

                assertThat(eqtokenTimeMillis).isLessThan(traditionalTimeMillis * 0.3);
            });
        });

    }

    public static void runTraditionalHashcodeManyTimes(){
        Object object = EqualityTraditionalImplementation.create();
        for (int i = 0; i < RUN_TIMES; i++) {
            object.hashCode();
        }

    }

    public static void runEqTokenHashcodeManyTimes(){
        Object object = ImmutableObjectEqTokenImplementation.create(EqualityTraditionalImplementation.create());
        for (int i = 0; i < RUN_TIMES; i++) {
            object.hashCode();
        }
    }

    public static void runMutableEqTokenHashcodeManyTimes(){
        Object object = MutableObjectEqTokenImplementation.create(EqualityTraditionalImplementation.create());
        for (int i = 0; i < RUN_TIMES; i++) {
            object.hashCode();
        }
    }

    public static void runTraditionalEqualsOnEqualObjectsManyTimes(){
        Object oneObject = EqualityTraditionalImplementation.create();
        Object otherObject = EqualityTraditionalImplementation.create();
        for (int i = 0; i < RUN_TIMES; i++) {
            oneObject.equals(otherObject);
        }

    }

    public static void runEqTokenEqualsOnEqualObjectsManyTimes(){
        Object oneObject = ImmutableObjectEqTokenImplementation.create(EqualityTraditionalImplementation.create());
        Object otherObject = ImmutableObjectEqTokenImplementation.create(EqualityTraditionalImplementation.create());
        for (int i = 0; i < RUN_TIMES; i++) {
            oneObject.equals(otherObject);
        }
    }

    public static void runMutableEqTokenEqualsOnEqualObjectsManyTimes(){
        Object oneObject = MutableObjectEqTokenImplementation.create(EqualityTraditionalImplementation.create());
        Object otherObject = MutableObjectEqTokenImplementation.create(EqualityTraditionalImplementation.create());
        for (int i = 0; i < RUN_TIMES; i++) {
            oneObject.equals(otherObject);
        }
    }


    public static void runTraditionalEqualsOnDifferentObjectsManyTimes(){
        Object oneObject = EqualityTraditionalImplementation.create();
        EqualityTraditionalImplementation otherObject = EqualityTraditionalImplementation.create();
        otherObject.getChildren().get(2).setText("Different");
        for (int i = 0; i < RUN_TIMES; i++) {
            oneObject.equals(otherObject);
        }

    }

    public static void runEqTokenEqualsOnDifferentObjectsManyTimes(){
        Object oneObject = ImmutableObjectEqTokenImplementation.create(EqualityTraditionalImplementation.create());

        EqualityTraditionalImplementation different = EqualityTraditionalImplementation.create();
        different.getChildren().get(0).setText("Different");
        // Because it's immutable we need to change the text before creating the instance
        Object otherObject = ImmutableObjectEqTokenImplementation.create(different);

        for (int i = 0; i < RUN_TIMES; i++) {
            oneObject.equals(otherObject);
        }

    }

    public static void runMutableEqTokenEqualsOnDifferentObjectsManyTimes(){
        Object oneObject = MutableObjectEqTokenImplementation.create(EqualityTraditionalImplementation.create());

        EqualityTraditionalImplementation different = EqualityTraditionalImplementation.create();
        Object otherObject = MutableObjectEqTokenImplementation.create(different);
        // Because it's mutable we can change the text after creating the instance
        different.getChildren().get(0).setText("Different");

        for (int i = 0; i < RUN_TIMES; i++) {
            oneObject.equals(otherObject);
        }

    }

}
