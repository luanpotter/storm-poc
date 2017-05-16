import org.apache.storm.topology.TopologyBuilder;

public class Main {

    public static void main(String[] args) {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("numbers", new NumbersSpout(), 10);
        builder.setBolt("isPrime", new IsPrimeBolt(), 3).shuffleGrouping("numbers");
    }
}
