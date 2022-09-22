import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.oracle.graal.compiler.enterprise.n;

public class Node {
  int val;
  LinkedList<Node> adj;
  //Useful when using BFS
  int dist;
  Node pred;
  //Useful when using DFS
  int start;
  int end;
}

public class Edge {
  Node n1;
  Node n2;
  boolean directed;
  int weight;
}

public class Graph {

  ArrayList<Node> nodes;

  public void bfs(Node node) {
    for(Node n : nodes) {
      n.dist = -1;
      n.pred = null;
    }
    node.dist = 0;
    node.pred = null;
    Queue<Node> queue = new LinkedList<>();
    queue.add(node);
    while(!queue.isEmpty()) {
      Node tmp = queue.removeFirst();
      for(Node n : tmp.adj) {
        if(n.dist == -1) {
          queue.add(n);
          n.dist = tmp.dist+1;
          n.pred = tmp;
        }
      }
    }
  }

  //Gives a shortest path from u to v
  public List<Node> shortestPath(Node u, Node v) {
    bfs(u);
    if(v.pred == null) { return null; }
    List<Node> path = new LinkedList<>();
    appendPath(u, v, path);
    return path;
  }

  private void appendPath(Node u, Node v, List<Node> path) {
    if(u.val == v.val) { path.add(u); }
    else {
      appendPath(u, v.pred, path);
      path.add(v);
    }
  }

  public void dfs(Node node) {
    for (Node n : nodes) {
      n.start = -1;
      n.end = -1;
    }
    applyDfs(node, new int[]{0});
  }

  private void applyDfs(Node node, int[] time) {
    node.start = time[0];
    time[0] += 1; 
    for(Node n : node.adj) {
      if(n.start == -1) {
        applyDfs(n, time);
      }
    }
    node.end = time[0];
    time[0] += 1;
  }

  //This assumes that the graph is a directed acyclic graph.
  //Resulting LinkedList gives an ordering of the nodes in terms of priority (start of LinkedList is top of dependency tree)
  public LinkedList<Node> topologicalSort() {
    for (Node n : nodes) {
      n.start = -1;
      n.end = -1;
    }
    LinkedList<Node> order = new LinkedList<>();
    int[] time = new int[]{0};
    for(Node n : nodes) {
      applyDfsLinked(n, time, order);
    }
    return order;
  }

  private void applyDfsLinked(Node node, int[] time, LinkedList<Node> order) {
    node.start = time[0];
    time[0] += 1;
    for(Node n : node.adj) {
      if(n.start != -1) {
        applyDfsLinked(node, time, order);
      }
    }
    order.addFirst(node);
    node.end = time[0];
    time[0] += 1;
  }

}

public class SpanningTrees {

  ArrayList<Node> nodes;
  ArrayList<Edge> edges;
  //UNION-FIND DATA STRUCTURE WITH PATH COMPRESSION AND UNION-BY-RANK
  //pred is used for refering to parent, dist is used for refering to rank
  public void makeSet(Node node) {
    node.pred = null;
    node.dist = 0;
  }

  public Node findSet(Node node) {
    if(node.pred == null) {
      return node;
    } else {
      node.pred = findSet(node.pred);
      return node.pred;
    }
  }

  public void union(Node n1, Node n2) {
    Node m1 = findSet(n1);
    Node m2 = findSet(n2);
    if(m1.dist >= m2.dist) {
      m2.pred = m1;
      if(m1.dist == m2.dist) {
        m1.dist += 1;
      }
    } else {
        m1.pred = m2;
    }
  }

  public ArrayList<Edge> kruskalMSP() {
    List<Edge> spanningTree = new ArrayList<>();
    for(Node n : nodes) {
      makeSet(n);
    }
    Collections.sort(edges, (e1, e2) -> e1.weight - e2.weight);
    for(Edge e : edges) {
      if(findSet(e.n1).val != findSet(e.n2).val) {
        spanningTree.add(e);
        union(e.n1, e.n2);
      }
    }
    return spanningTree;
  }

  public ArrayList<Edge> primMSP(Node start) {
    for(Node n : nodes) {
      n.dist = Integer.MAX_VALUE;
      n.pred = null;
    }
    start.dist = 0;
    // Heap unhandled = new Heap(nodes);
    // unhandled.buildMinHeap(); // according to node.dist, NOT node.val
    // while(!unhandled.isEmpty()) {
    //   Node n = unhandled.minHeapRemoveMin();
    //   for(Node neigh : n.adj) {
    //     if(neigh.dist > n.dist+weight(n, neigh)) {
    //       neigh.dist = n.dist+weight(n, neigh);
    //       neigh.pred = n; //Min Heap property needs to be preserved
    //     }
    //   }
    // }
    // //The minimum spanning tree is the one formed by the edges (u, u.pred) for all u in V
    // ArrayList<Edge> spanningTree = new ArrayList<Edge>();
    // for(Node n : nodes) {
    //   if(n.val != start.val) {
    //     spanningTree.add((n, n.pred));
    //   }
    // }
    // return spanningTree;
    return null;
  }

  // Directed graph
  public boolean bellmanFord(Node root) { // O(VE)
    for(Node n : nodes) {
      n.dist = Integer.MAX_VALUE;
      n.pred = null;
    }
    root.dist = 0;
    for(int i=0; i<nodes.size()-1; ++i) {
      for(Edge e : edges) {
        if(e.n1.dist > e.n2.dist + e.weight) {
          e.n1.dist = e.n2.dist + e.weight;
          e.n1.pred = e.n2;
        }
      }
    }
    for(Edge e : edges) {
      if(e.n1.dist > e.n2.dist + e.weight) { // If one can still improve after |V|-1 steps, then there is a negative-weight cycle, THERE ARE NO SHORTEST PATHS IN THIS CASE
        return false;
      }
    }
    return true; // No negative-weight cycle
  }

  // Directed graph
  public void shortestPathDAG(Node root) { // O(V+E)
    for(Node n : nodes) {
      n.dist = Integer.MAX_VALUE;
      n.pred = null;
    }
    root.dist = 0;
    // LinkedList<Node> sortedNodes = topologicalSort();
    // boolean rootFound = false;
    // for(Node n : sortedNodes) {
    //   if(n.val == root.val) { rootFound = true; }
    //   if(rootFound) {
    //     for(Node neigh : n.adj) {
    //       if(neigh.dist > n.dist + weight(n, neigh)) {
    //         neigh.dist = n.dist + weight(n, neigh);
    //         neigh.pred = n;
    //       }
    //     }
    //   }
    // }
  }

  // Directed positively-weighted graph
  public void dijkstra(Node root) {
    for(Node n : nodes) {
      n.dist = Integer.MAX_VALUE;
      n.pred = null;
    }
    root.dist = 0;
    // Heap unhandled = new Heap(nodes);
    // unhandled.buildMinHeap();
    // while(!unhandled.isEmpty()) {
    //   Node tmp = unhandled.minHeapRemoveMin();
    //   for(Node n : tmp.adj) {
    //     if(n.dist > tmp.dist + weight(tmp, n)) {
    //       n.dist = tmp.dist + weight(tmp, n);
    //       n.pred = tmp;
    //     }
    //   }
    // }
  }
}