import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.stream.Collectors;

public class IsPrimeBolt extends BaseBasicBolt {
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        int number = (int) tuple.getValueByField("number");
        if (isPrime(number)) {
            System.out.println("-------- IS PRIME" + number);
            basicOutputCollector.emit(new Values(number));
        }
    }

    private boolean isPrime(int n) {
        if (n % 2 == 0) return false;
        for (int i = 3; i <= Math.sqrt(n); i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("number"));
    }
}
