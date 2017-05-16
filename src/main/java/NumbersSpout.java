import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class NumbersSpout extends BaseRichSpout {

    private final int max;
    private SpoutOutputCollector collector;

    private static final AtomicInteger count = new AtomicInteger();

    public NumbersSpout(int max) {
        this.max = max;
    }

    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector collector) {
        this.collector = collector;
    }

    public void nextTuple() {
        int n = count.getAndIncrement();
        if (n >= max) {
            this.close();
            return;
        }
        int number = create(n);
        System.out.println("-------- EMIT" + number);
        collector.emit(new Values(number));
    }

    private int create(int n) {
        return factorial(n) + n + 1;
    }

    private static Integer factorial(Integer n) {
        if (n <= 1) {
            return 1;
        }
        return n * factorial(n - 1);
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("number"));
    }
}
