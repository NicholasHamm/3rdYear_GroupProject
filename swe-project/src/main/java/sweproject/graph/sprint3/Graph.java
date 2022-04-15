package sweproject.graph.sprint3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface Graph {

    public Map<String, Map<String, Integer>> getEdges();
    public Map<String, Map<String, Integer>> invert();
    public List<Evangelists> getEvangelists();

    Map<Edge, List<Arc>> adjVertices = new HashMap <Edge, List<Arc>>();

}