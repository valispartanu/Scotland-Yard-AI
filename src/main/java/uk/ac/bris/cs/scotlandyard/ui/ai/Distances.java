package uk.ac.bris.cs.scotlandyard.ui.ai;

import uk.ac.bris.cs.gamekit.graph.*;
import uk.ac.bris.cs.scotlandyard.model.Transport;

import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

class Distances {

    private Cost cost;
    private int Xloc, detLoc;
    private Graph<Integer, Transport> graph;
    private int distance = -1;
    private Cost tickets;

    Distances(int mrXLocation, int detectiveLocation, Graph<Integer, Transport> graph, Cost availableTickets) {
        this.graph = graph;
        Xloc = mrXLocation;
        detLoc = detectiveLocation;
        cost = new Cost();
        tickets = availableTickets;
    }

    private Boolean hasEnoughTickets(Cost c) {
        return tickets.hasBus(c.getBus()) && tickets.hasTaxi(c.getTaxi()) && tickets.hasUnderground(c.getUnderground());
    }

    private class Node {

        Cost cost;
        int node;

        Node(int start) {
            cost = new Cost();
            node = start;
        }

        Node(Node n) {
            cost = new Cost(n.cost);
            node = n.node;
        }
    }

    private void BFS(int start, int finish) {

        Queue<Node> q = new ConcurrentLinkedQueue<>();
        q.add(new Node(start));
        boolean[] viz = new boolean[200];
        viz[start] = true;
        while (!q.isEmpty()) {
            Node node = q.poll();
            if (node.node == finish) {
                cost = node.cost;
                distance = node.cost.getMoves();
            }
            viz[node.node] = true;
            if (node.node == finish && node.cost.getMoves() > distance)
                return;

            Collection<Edge<Integer, Transport>> edges = graph.getEdgesFrom(new uk.ac.bris.cs.gamekit.graph.Node<>(node.node));
            for (Edge<Integer, Transport> edge : edges) {
                Transport t = edge.data();
                int destination = edge.destination().value();

                Node newNode = new Node(node);
                newNode.node = destination;
                newNode.cost.addTransport(t);

                if (hasEnoughTickets(newNode.cost) && !viz[newNode.node]) {
                    q.add(newNode);
                }
            }
        }
    }


    Cost getCost() {
        if (distance == -1)
            BFS(detLoc, Xloc);
        return cost;
    }


}
