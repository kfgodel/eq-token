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

        describe("an immutable complex object", ()->{

            it("should produce same hashcode for eqtoken and traditional approach", ()->{
                int traditionalHashcode = ImmutableObjectTraditionImplementation.create().hashCode();
                int eqHashcode = ImmutableObjectEqToken.create(ImmutableObjectTraditionImplementation.create()).hashCode();

                assertThat(traditionalHashcode).isEqualTo(eqHashcode);
            });

            it("should produce a faster hashcode with eq-token than traditional approach", () -> {
                MicroBenchmark bench = MicroBenchmark.create();

                double traditionalTimeMillis = bench.measure("Immutable hashcode Traditional", PerformanceTest::runTraditionalHashcodeManyTimes);
                double eqtokenTimeMillis = bench.measure("Immutable hashcode EqToken", PerformanceTest::runEqTokenHashcodeManyTimes);

                bench.printResults();

                assertThat(eqtokenTimeMillis).isLessThan(traditionalTimeMillis);
            });

            it("should run slightly slower equals for equals objects with eq-token implementation", ()->{
                MicroBenchmark bench = MicroBenchmark.create();

                double traditionalTimeMillis = bench.measure("Immutable equals Traditional (=)", PerformanceTest::runTraditionalEqualsOnEqualObjectsManyTimes);
                double eqtokenTimeMillis = bench.measure("Immutable equals EqToken (=)", PerformanceTest::runEqTokenEqualsOnEqualObjectsManyTimes);

                bench.printResults();

                assertThat(eqtokenTimeMillis).isLessThan(traditionalTimeMillis * 1.1);
            });

            it("should run faster equals for different objects with eq-token implementation", ()->{
                MicroBenchmark bench = MicroBenchmark.create();

                double traditionalTimeMillis = bench.measure("Immutable equals Traditional (!=)", PerformanceTest::runTraditionalEqualsOnDifferentObjectsManyTimes);
                double eqtokenTimeMillis = bench.measure("Immutable equals EqToken (!=)", PerformanceTest::runEqTokenEqualsOnDifferentObjectsManyTimes);

                bench.printResults();

                assertThat(eqtokenTimeMillis).isLessThan(traditionalTimeMillis);
            });

        });

        describe("a mutable complex object", ()->{
        });

    }

    public static void runTraditionalHashcodeManyTimes(){
        Object object = ImmutableObjectTraditionImplementation.create();
        for (int i = 0; i < RUN_TIMES; i++) {
            object.hashCode();
        }

    }

    public static void runEqTokenHashcodeManyTimes(){
        Object object = ImmutableObjectEqToken.create(ImmutableObjectTraditionImplementation.create());;
        for (int i = 0; i < RUN_TIMES; i++) {
            object.hashCode();
        }
    }

    public static void runTraditionalEqualsOnEqualObjectsManyTimes(){
        Object oneObject = ImmutableObjectTraditionImplementation.create();
        Object otherObject = ImmutableObjectTraditionImplementation.create();
        for (int i = 0; i < RUN_TIMES; i++) {
            oneObject.equals(otherObject);
        }

    }

    public static void runEqTokenEqualsOnEqualObjectsManyTimes(){
        Object oneObject = ImmutableObjectEqToken.create(ImmutableObjectTraditionImplementation.create());
        Object otherObject = ImmutableObjectEqToken.create(ImmutableObjectTraditionImplementation.create());
        for (int i = 0; i < RUN_TIMES; i++) {
            oneObject.equals(otherObject);
        }

    }

    public static void runTraditionalEqualsOnDifferentObjectsManyTimes(){
        Object oneObject = ImmutableObjectTraditionImplementation.create();
        ImmutableObjectTraditionImplementation otherObject = ImmutableObjectTraditionImplementation.create();
        otherObject.getChildren().get(2).setText("Different");
        for (int i = 0; i < RUN_TIMES; i++) {
            oneObject.equals(otherObject);
        }

    }

    public static void runEqTokenEqualsOnDifferentObjectsManyTimes(){
        Object oneObject = ImmutableObjectEqToken.create(ImmutableObjectTraditionImplementation.create());

        ImmutableObjectTraditionImplementation different = ImmutableObjectTraditionImplementation.create();
        different.getChildren().get(2).setText("Different");
        Object otherObject = ImmutableObjectEqToken.create(different);

        for (int i = 0; i < RUN_TIMES; i++) {
            oneObject.equals(otherObject);
        }

    }

}
