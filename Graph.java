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

}