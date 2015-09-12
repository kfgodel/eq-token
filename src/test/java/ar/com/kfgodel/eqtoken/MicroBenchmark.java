package ar.com.kfgodel.eqtoken;

import etm.core.configuration.BasicEtmConfigurator;
import etm.core.configuration.EtmManager;
import etm.core.monitor.EtmMonitor;
import etm.core.monitor.EtmPoint;
import etm.core.renderer.SimpleTextRenderer;

/**
 * This type allows to measure runtime on different alternatives and print their result
 * Created by tenpines on 12/09/15.
 */
public class MicroBenchmark {

    private EtmMonitor monitor;

    public static MicroBenchmark create(){
        EtmManager.reset();
        BasicEtmConfigurator.configure();
        MicroBenchmark benchmark = new MicroBenchmark();
        benchmark.monitor = EtmManager.getEtmMonitor();
        benchmark.monitor.start();
        return benchmark;
    }

    public double measure(String testName, Runnable testedCode) {
        EtmPoint point = monitor.createPoint(testName);

        try{
            testedCode.run();

        }finally {
            point.collect();
        }
        return point.getTransactionTime();
    }

    public void printResults() {
        monitor.render(new SimpleTextRenderer());
        monitor.stop();
    }
}
