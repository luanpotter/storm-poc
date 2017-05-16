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
        int max = Integer.parseInt(args[0]);
        int sleep = Integer.parseInt(args[1]);

        StormTopology topology = createTopology(max);

        LocalCluster cluster = new LocalCluster();
        Config config = new Config();
        config.setDebug(true);
        cluster.submitTopology("storm-test", config, topology);
        Utils.sleep(sleep);
        System.out.println("-------- RESULT" + Counter.TOTAL_SUM.get());
        cluster.killTopology("storm-test");
        cluster.shutdown();
    }

    private static StormTopology createTopology(int max) {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("numbers", new NumbersSpout(max), 10);
        builder.setBolt("isPrime", new IsPrimeBolt(), 3).shuffleGrouping("numbers");
        builder.setBolt("sum", new Counter(), 1).shuffleGrouping("isPrime");

        return builder.createTopology();
    }
}
