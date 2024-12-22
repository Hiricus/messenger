package com.pavmaxdav.mymess.dto;

import com.pavmaxdav.mymess.entity.attached.ResourceType;

import java.util.Base64;

public class AttachedResourceDTO {
    private String name = "";
    private String resource = "";
    private ResourceType resourceType = ResourceType.IMAGE;

    // Конструкторы
    public AttachedResourceDTO() {}
    public AttachedResourceDTO(String name, String resource, ResourceType resourceType) {
        this.name = name;
        this.resource = resource;
        this.resourceType = resourceType;
    }

    // Геттеры
    public String getName() {
        if (name == null) {
            return null;
        }
        return name;
    }
    public String getResource() {
        if (resource == null) {
            return null;
        }
        return resource;
    }
    public byte[] getResourceByte() {
        if (resource == null) {
            return null;
        }
        return Base64.getDecoder().decode(resource);
    }
    public ResourceType getResourceType() {
        if (resourceType == null) {
            return null;
        }
        return resourceType;
    }

    // Сеттеры
    public void setName(String name) {
        this.name = name;
    }
    public void setResource(String resource) {
        this.resource = resource;
    }
    public void setResource(byte[] resource) {
        this.resource = Base64.getEncoder().encodeToString(resource);
    }
    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }


    @Override
    public String toString() {
        return "AttachedResourceDTO{" +
                "name='" + name + '\'' +
                ", resource='" + resource + '\'' +
                ", resourceType=" + resourceType +
                '}';
    }
}
