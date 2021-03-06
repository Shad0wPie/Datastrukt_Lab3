import java.util.*;


/**
 * A directed weighted graph
 * @param <E> value type of each node
 */
public class Network<E> {

    private ArrayList<Node<E>> nodes;
    private HashMap<E, Node<E>> nodeMap;

    /**
     * Creates a network
     *
     * @param nodeValues Values of each node
     */
    public Network(List<E> nodeValues) {
        nodes = new ArrayList<>(nodeValues.size());
        nodeMap = new HashMap<>(nodeValues.size());

        for (E value : nodeValues) {
            Node<E> node = new Node<>(value);
            nodes.add(node);
            nodeMap.put(value, node);
        }
    }

    /**
     * Adds a connection between two nodes.
     *
     * @param A      The first node
     * @param B      The second node
     * @param weight The weight of the conneciotn.
     */
    public void addConnection(E A, E B, int weight) {
        Node<E> nodeA = nodeMap.get(A);
        Node<E> nodeB = nodeMap.get(B);

        nodeA.addAdjacent(nodeB, weight);
    }

    /**
     * Returns the node that mathces the value
     *
     * @param value The value of the node
     * @return Returns a node
     */
    public Node<E> getNode(E value) {
        return nodeMap.get(value);
    }

    /**
     * Returns an iterator for all nodes.
     *
     * @return Return nodes
     */
    public Iterator<Node<E>> getNodes() {
        return nodes.iterator();
    }

    /**
     * Prints the network as a string.
     */
    public void printNetwork() {
        Iterator<Node<E>> nodesItr = getNodes();
        while (nodesItr.hasNext()) {
            Node<E> next = nodesItr.next();
            System.out.println(next.value.toString() + " has connections:");
            Iterator<Pair<Node<E>, Integer>> adjItr = next.getAdjacents();
            while (adjItr.hasNext()) {
                System.out.println(adjItr.next().toString());
            }
        }
    }

    public static class Node<E> {
        public final E value; //value of the node
        private final int hash; //stores the hash for optimisation (maybe already happens in java?)
        private HashMap<Node<E>, Pair<Node<E>, Integer>> adjacents; //adjacent nodes

        /**
         * Creates a node.
         *
         * @param value The value of the node.
         */
        public Node(E value) {
            this.value = value;
            this.hash = value.hashCode();
            this.adjacents = new HashMap<>();
        }

        /**
         * Adds an adjacent. Checks if this connection already exists and if it does it chooses the
         * one with the smallest weight.
         * @param node The adjacent.
         * @param weight The weight of the connection from the node to the adjacent.
         */
        protected void addAdjacent(Node<E> node, Integer weight) {
            Pair<Node<E>, Integer> newNode = new Pair<>(node, weight);
            Pair<Node<E>, Integer> oldNode = adjacents.get(node);
            if (oldNode == null) {
                adjacents.put(node, newNode);
            } else if (oldNode.second > newNode.second) {
                adjacents.put(node, newNode);
            }
        }

        /**
         * Returns an iterator for all nodes.
         * @return Returns a node.
         */
        public Iterator<Pair<Node<E>, Integer>> getAdjacents() {
            return adjacents.values().iterator();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Node<?> node = (Node<?>) o;

            return hashCode() == node.hashCode() && value == node.value;
        }

        /**
         * @return the hash (constant since the value is stored).
         */
        @Override
        public int hashCode() {
            return hash;
        }

        @Override
        public String toString() {
            return value.toString();
        }
    }
}

