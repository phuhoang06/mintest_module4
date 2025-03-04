package com.codegym.model;

import javax.persistence.*;

@Entity
@Table(name = "computer")
public class Computer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
    private String producer;
    @ManyToOne
    @JoinColumn(name = "computerType_id")
    private Type computerType;

    public Computer() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getProducer() { return producer; }
    public void setProducer(String producer) { this.producer = producer; }
    public Type getComputerType() { return computerType; }
    public void setComputerType(Type computerType) { this.computerType = computerType; }
}
