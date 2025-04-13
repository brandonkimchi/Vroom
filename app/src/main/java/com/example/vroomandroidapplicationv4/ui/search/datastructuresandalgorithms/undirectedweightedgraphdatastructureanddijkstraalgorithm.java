package com.example.vroomandroidapplicationv4.ui.search.datastructuresandalgorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class undirectedweightedgraphdatastructureanddijkstraalgorithm {
    private Map<String, List<Edge>> graph;

    public undirectedweightedgraphdatastructureanddijkstraalgorithm() {
        graph = new HashMap<>();
        buildGraph(); // Automatically populate graph on instantiation
    }

    // Automatically populate nodes & edges
    private void buildGraph() {
        // Western side
        addEdge("Jurong West", "Woodlands", 8);
        addEdge("Woodlands", "Jurong West", 8);

        addEdge("Jurong West", "Bukit Batok", 7);
        addEdge("Bukit Batok", "Jurong West", 7);

        addEdge("Woodlands", "Yishun", 4);
        addEdge("Yishun", "Woodlands", 4);

        addEdge("Woodlands", "Bukit Panjang", 5);
        addEdge("Bukit Panjang", "Woodlands", 5);

        addEdge("Yishun", "Bukit Panjang", 4);
        addEdge("Bukit Panjang", "Yishun", 4);

        addEdge("Bukit Panjang", "Bukit Batok", 4);
        addEdge("Bukit Batok", "Bukit Panjang", 4);

        addEdge("Jurong West", "Bukit Batok", 7);
        addEdge("Bukit Batok", "Jurong West", 7);

        addEdge("Clementi", "Bukit Batok", 3);
        addEdge("Bukit Batok", "Clementi", 3);

        addEdge("Bukit Panjang", "Bukit Timah", 3);
        addEdge("Bukit Timah", "Bukit Panjang", 3);

        addEdge("Bukit Timah", "Novena", 1);
        addEdge("Novena", "Bukit Timah", 1);

        addEdge("Bukit Timah", "Clementi", 3);
        addEdge("Clementi", "Bukit Timah", 3);

        addEdge("Clementi", "Queenstown", 3);
        addEdge("Queenstown", "Clementi", 3);


        // Central / North
        addEdge("Yishun", "Ang Mo Kio", 5);
        addEdge("Ang Mo Kio", "Yishun", 5);

        addEdge("Bukit Panjang", "Ang Mo Kio", 2);
        addEdge("Ang Mo Kio", "Bukit Panjang", 2);

        addEdge("Ang Mo Kio", "Bishan", 1);
        addEdge("Bishan", "Ang Mo Kio", 1);

        addEdge("Bukit Timah", "Bishan", 2);
        addEdge("Bishan", "Bukit Timah", 2);

        addEdge("Ang Mo Kio", "Bishan", 1);
        addEdge("Bishan", "Ang Mo Kio", 1);

        addEdge("Bishan", "Toa Payoh", 1);
        addEdge("Toa Payoh", "Bishan", 1);

        addEdge("Toa Payoh", "Novena", 1);
        addEdge("Novena", "Toa Payoh", 1);

        addEdge("Novena", "Kallang", 3);
        addEdge("Kallang", "Novena", 3);

        addEdge("Novena", "Queenstown", 3);
        addEdge("Queenstown", "Novena", 3);

        addEdge("Kallang", "Geylang", 3);
        addEdge("Geylang", "Kallang", 3);

        addEdge("Toa Payoh", "Geylang", 3);
        addEdge("Geylang", "Toa Payoh", 3);



        // Northeast / East
        addEdge("Yishun", "Sengkang", 9);
        addEdge("Sengkang", "Yishun", 9);

        addEdge("Serangoon", "Sengkang", 4);
        addEdge("Sengkang", "Serangoon", 4);

        addEdge("Serangoon", "Ang Mo Kio", 2);
        addEdge("Ang Mo Kio", "Serangoon", 2);

        addEdge("Serangoon", "Toa Payoh", 3);
        addEdge("Toa Payoh", "Serangoon", 3);

        addEdge("Sengkang", "Punggol", 4);
        addEdge("Punggol", "Sengkang", 4);

        addEdge("Sengkang", "Hougang", 5);
        addEdge("Hougang", "Sengkang", 5);

        addEdge("Hougang", "Geylang", 4);
        addEdge("Geylang", "Hougang", 4);

        addEdge("Hougang", "Marine Parade", 6);
        addEdge("Marine Parade", "Hougang", 6);

        // East coast & Marine Parade side
        addEdge("Geylang", "Marine Parade", 3);
        addEdge("Marine Parade", "Geylang", 3);

        addEdge("Marine Parade", "Bedok", 4);
        addEdge("Bedok", "Marine Parade", 4);

        addEdge("Bedok", "Hougang", 5);
        addEdge("Hougang", "Bedok", 5);

        // Far East
        addEdge("Bedok", "Tampines", 5);
        addEdge("Tampines", "Bedok", 5);

        addEdge("Tampines", "Pasir Ris", 5);
        addEdge("Pasir Ris", "Tampines", 5);

        addEdge("Pasir Ris", "Punggol", 6);
        addEdge("Punggol", "Pasir Ris", 6);

        addEdge("Tampines", "Changi", 4);
        addEdge("Changi", "Tampines", 4);

        addEdge("Pasir Ris", "Changi", 4);
        addEdge("Changi", "Pasir Ris", 4);
    }

    // Core graph logic
    private void addEdge(String from, String to, int weight) {
        if (!graph.containsKey(from)) {
            graph.put(from, new ArrayList<>());
        }
        if (!graph.containsKey(to)) {
            graph.put(to, new ArrayList<>());
        }

        graph.get(from).add(new Edge(to, weight));
        graph.get(to).add(new Edge(from, weight));
    }

    // Dijkstra algorithm
    public Map<String, Integer> dijkstra(String startNode) {
        Map<String, Integer> distances = new HashMap<>();
        for (String node : graph.keySet()) {
            distances.put(node, Integer.MAX_VALUE);
        }
        distances.put(startNode, 0);

        PriorityQueue<Edge> pq = new PriorityQueue<>();
        pq.offer(new Edge(startNode, 0));

        while (!pq.isEmpty()) {
            Edge current = pq.poll();
            if (current.weight > distances.get(current.node)) continue;

            for (Edge neighbor : graph.get(current.node)) {
                int newDist = current.weight + neighbor.weight;
                if (newDist < distances.get(neighbor.node)) {
                    distances.put(neighbor.node, newDist);
                    pq.offer(new Edge(neighbor.node, newDist));
                }
            }
        }

        return distances;
    }


    // Inner Edge class
    private static class Edge implements Comparable<Edge> {
        String node;
        int weight;

        Edge(String node, int weight) {
            this.node = node;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge other) {
            return Integer.compare(this.weight, other.weight);
        }
    }
}
