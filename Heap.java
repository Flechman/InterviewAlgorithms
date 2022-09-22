import java.util.List;

public class Heap {
  List<Integer> arr;

  public Heap() {
    arr = new ArrayList<Integer>();
  }

  public Heap(List<Integer> arr) {
    this.arr = arr;
  }

  public void maxHeapInsert(int e) {
    arr.add(e);
    int i = arr.size()-1;
    while(i > 0 && arr.get(parent(i)) < e) {
      arr.set(i, arr.get(parent(i)));
      arr.set(parent(i), e);
      i = parent(i);
    }
  }

  public void minHeapInsert(int e) {
    arr.add(e);
    int i = arr.size()-1;
    while(i > 0 && arr.get(parent(i)) > e) {
      arr.set(i, arr.get(parent(i)));
      arr.set(parent(i), e);
      i = parent(i);
    }
  }

  public int maxHeapRemoveMax() {
    maxHeapify(0);
    int max = maxHeapMax();
    arr.set(0, arr.get(arr.size()-1));
    arr.remove(arr.size()-1); //O(1) when index is last element
    maxHeapify(0);
    return max;
  }

  public int minHeapRemoveMin() {
    minHeapify(0);
    int min = minHeapMin();
    arr.set(0, arr.get(arr.size() - 1));
    arr.remove(arr.size() - 1); // O(1) when index is last element
    minHeapify(0);
    return min;
  }

  public int maxHeapMax() {
    return arr.get(0);
  }

  public int minHeapMin() {
    return arr.get(0);
  }

  public int size() {
    return arr.size();
  }

  public int parent(int i) {
    if(i == 0) { return -1; }
    return (i-1)/2;
  }

  public int left(int i) {
    return 2*i + 1;
  }

  public int right(int i) {
    return 2*i + 2;
  }

  // O(n) runtime, by a non-trivial tight analysis
  public void buildMaxHeap() {
    for(int i = arr.size() / 2; i >= 0; --i) {
      maxHeapify(i);
    }
  }

  // O(n) runtime, by a non-trivial tight analysis
  public void buildMinHeap() {
    for(int i = arr.size() / 2; i >= 0; --i) {
      minHeapify(i);
    }
  }

  // Assumes subtree verifies max heap property
  // O(log(n)) runtime
  public void maxHeapify(int i) {
    if(i < arr.size()) {
      int biggest = arr.get(i);
      int swapWith = i;
      if(left(i) < arr.size() && arr.get(left(i)) > biggest) {
        biggest = arr.get(left(i));
        swapWith = left(i);
      }
      if(right(i) < arr.size() && arr.get(right(i)) > biggest) {
        biggest = arr.get(right(i));
        swapWith = right(i);
      }
      if(swapWith != i) {
        arr.set(swapWith, arr.get(i));
        arr.set(i, biggest);
        maxHeapify(swapWith);
      }
    }
  }

  // Assumes subtree verifies min heap property
  // O(log(n)) runtime
  public void minHeapify(int i) {
    if (i < arr.size()) {
      int smallest = arr.get(i);
      int swapWith = i;
      if (left(i) < arr.size() && arr.get(left(i)) < smallest) {
        smallest = arr.get(left(i));
        swapWith = left(i);
      }
      if (right(i) < arr.size() && arr.get(right(i)) < smallest) {
        smallest = arr.get(right(i));
        swapWith = right(i);
      }
      if (swapWith != i) {
        arr.set(swapWith, arr.get(i));
        arr.set(i, smallest);
        minHeapify(swapWith);
      }
    }
  } 
}
