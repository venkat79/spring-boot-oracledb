package com.exacs.ecra.entities.model;

import com.exacs.ecra.entities.enums.RackType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "rack")
public class Rack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "rack_type")
    @Enumerated(EnumType.STRING)
    private RackType rackType;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "exadata_rack_id")
    private Set<ComputeNode> computeNodeList = new HashSet();

    public void addNode(ComputeNode computeNode) {
        computeNodeList.add(computeNode);
        computeNode.setRack(this);
    }


    public void removeNode(ComputeNode computeNode) {
        computeNodeList.remove(computeNode);
        computeNode.setRack(null);
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RackType getRackType() {
        return rackType;
    }

    public void setRackType(RackType rackType) {
        this.rackType = rackType;
    }

    public Set<ComputeNode> getComputeNodeList() {
        return computeNodeList;
    }

    public void setComputeNodeList(Set<ComputeNode> computeNodeList) {
        this.computeNodeList = computeNodeList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rack rack = (Rack) o;
        return Objects.equals(name, rack.name) &&
                rackType == rack.rackType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Rack{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rackType=" + rackType +
                ", computeNodeList=" + computeNodeList +
                '}';
    }
}
