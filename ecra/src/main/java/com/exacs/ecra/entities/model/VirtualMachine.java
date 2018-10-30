package com.exacs.ecra.entities.model;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Table(name = "virtual_machine")
public class VirtualMachine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rack_slot_id")
    private RackSlot rackSlot;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private ComputeNode computeNode;

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

    public RackSlot getRackSlot() {
        return rackSlot;
    }

    public void setRackSlot(RackSlot rackSlot) {
        this.rackSlot = rackSlot;
    }

    public ComputeNode getComputeNode() {
        return computeNode;
    }

    public void setComputeNode(ComputeNode computeNode) {
        this.computeNode = computeNode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VirtualMachine that = (VirtualMachine) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "VirtualMachine{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rackSlot=" + rackSlot +
                ", computeNode=" + computeNode +
                '}';
    }
}
