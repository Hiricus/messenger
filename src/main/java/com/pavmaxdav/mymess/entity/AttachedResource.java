package com.pavmaxdav.mymess.entity;

import com.pavmaxdav.mymess.entity.attached.ResourceType;
import jakarta.persistence.*;

@Entity
@Table(name = "attached_resources")
public class AttachedResource {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "resource", columnDefinition = "MEDIUMBLOB")
    private byte[] resource = new byte[0];;

    @Column(name = "resource_type")
    private ResourceType resourceType = ResourceType.IMAGE;

    @OneToOne
    @JoinColumn(name = "message_id", referencedColumnName = "id", nullable = true)
    private Message message;


    // Конструкторы
    public AttachedResource() {}
    public AttachedResource(String name, byte[] resource, ResourceType resourceType) {
        this.name = name;
        this.resource = resource;
        this.resourceType = resourceType;
    }

    // Геттеры
    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public byte[] getResource() {
        return resource;
    }
    public ResourceType getResourceType() {
        return resourceType;
    }

    // Сеттеры
    public void setId(Integer id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setResource(byte[] resource) {
        this.resource = resource;
    }
    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }
}
