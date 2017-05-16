import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.Map;
import java.util.Random;

public class NumbersSpout extends BaseRichSpout {

    private SpoutOutputCollector collector;
    private Integer n;

    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector collector) {
        this.collector = collector;
        this.n = 0;
    }

    public void nextTuple() {
        collector.emit(new Values(factorial(n) + n));
    }

    private static Integer factorial(Integer n) {
        if (n <= 1) {
            return 1;
        }
        return n * factorial(n - 1);
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("numbers"));
    }
}
