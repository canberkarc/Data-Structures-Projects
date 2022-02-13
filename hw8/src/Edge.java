public class Edge<E> {
    // Data Fields
    /** The source vertex */
    private E source;
  
    /** The destination vertex */
    private E dest;
  
    /** The weight */
    private int weight;

    /** The distance */
    private E distance;

    /** The time */
    private E time;

    /** The quality */
    private E quality;

    // Constructor
    /** Construct an Edge with a source of from
        and a destination of to. Set the weight
        to 1.0.
     * @param source - The source vertex
     * @param dest - The destination vertex
     */
    public Edge(E source, E dest) {
      this.source = source;
      this.dest = dest;
      weight = 1;
    }
  
    /** Construct a weighted edge with a source
        of from and a destination of to. Set the
        weight to w.
        @param source - The source vertex
        @param dest - The destination vertex
        @param v - The value
     */
    public Edge(E source, E dest, E v) {
      this.source = source;
      this.dest = dest;
      weight = (int) v;
      time = v;
      distance = v;
      quality = v;
    }

    // Methods
    /** Get the source
        @return The value of source
     */
    public E getSource() {
      return source;
    }
  
    /** Get the destination
        @return The value of dest
     */
    public E getDest() {
      return dest;
    }
  
    /** Get the weight
        @return the value of weight
     */
    public int getWeight() {
      return weight;
    }
  
    /** Return a String representation of the edge
        @return A String representation of the edge
     */
    public String toString() {
      StringBuffer sb = new StringBuffer("[(");
      sb.append(source.toString());
      sb.append(", ");
      sb.append(dest.toString());
      sb.append("): ");
      sb.append(Double.toString(weight));
      sb.append("]");
      return sb.toString();
    }
  
    /** Return true if two edges are equal. Edges
        are equal if the source and destination
        are equal. Weight is not considered.
        @param obj The object to compare to
        @return true if the edges have the same source
        and destination
     */
    public boolean equals(Object obj) {
      if (obj instanceof Edge) {
        Edge edge = (Edge) obj;
        return (source == edge.source
                && dest == edge.dest);
      }
      else {
        return false;
      }
    }
  
    /** Return a hash code for an edge.  The hash
        code is the source shifted left 16 bits
        exclusive or with the dest
        @return a hash code for an edge
     */
    public int hashCode() {
      return ((Integer) source << 16) ^ (Integer)dest;
    }

    /** Getter of distance
     * @return distance
     */
    public int getDistance() {
        return (int)distance;
    }

    /** Setter of distance
     * @param distance
     */
    public void setDistance(E distance) {
        this.distance = distance;
    }

    /** Getter of time
     * @return time
     */
    public int getTime() {
        return (int)time;
    }

    /** Setter of time
     * @param time
     */
    public void setTime(E time) {
        this.time = time;
    }

    /** Getter of quality
     * @return quality
     */
    public int getQuality() {
        return (int)quality;
    }

    /** Setter of quality
     * @param quality
     */
    public void setQuality(E quality) {
        this.quality = quality;
    }
}

