import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;

import java.util.concurrent.atomic.AtomicLong;

public class Counter extends BaseBasicBolt {

    public static final AtomicLong TOTAL_SUM = new AtomicLong();

    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        int number = (int) tuple.getValueByField("number");
        System.out.println("-------- SUMMING" + number);
        TOTAL_SUM.addAndGet(number);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
    }
}
