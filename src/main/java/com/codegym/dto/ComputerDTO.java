package com.codegym.dto;

public class ComputerDTO {
    private Long id;
    private String code;
    private String name;
    private String producer;
    private Long typeId; // Chỉ lưu ID của Type

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getProducer() { return producer; }
    public void setProducer(String producer) { this.producer = producer; }
    public Long getTypeId() { return typeId; }
    public void setTypeId(Long typeId) { this.typeId = typeId; }
}
