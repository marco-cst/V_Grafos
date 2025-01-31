package controller.tda.graph;

public class Adjacency {
    private Integer destination;
    private Float weight;


    public Adjacency(Integer destination, Float weight) {
        this.destination = destination;
        this.weight = weight;
    }

    public Adjacency(){}

    public Integer getDestination() {
        return destination;
    }
    public void setDestination(Integer destination) {
        this.destination = destination;
    }
    public Float getWeight() {
        return weight;
    }
    public void setWeight(Float weight) {
        this.weight = weight;
    }
}
