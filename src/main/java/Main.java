import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.utils.Utils;

public class Main {

    public static void main(String[] args) throws InvalidTopologyException, AuthorizationException, AlreadyAliveException {
        StormTopology topology = createTopology();

        LocalCluster cluster = new LocalCluster();
        Config config = new Config();
        config.setDebug(true);
        cluster.submitTopology("storm-test", config, topology);
        Utils.sleep(20000);
        System.err.println(Counter.TOTAL_SUM.get());
        cluster.killTopology("storm-test");
        cluster.shutdown();
    }

    private static StormTopology createTopology() {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("numbers", new NumbersSpout(10), 10);
        builder.setBolt("isPrime", new IsPrimeBolt(), 3).shuffleGrouping("numbers");
        builder.setBolt("sum", new Counter(), 1).shuffleGrouping("isPrime");

        return builder.createTopology();
    }
}
