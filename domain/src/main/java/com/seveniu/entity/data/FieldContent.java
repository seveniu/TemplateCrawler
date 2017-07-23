package com.seveniu.entity.data;

import com.seveniu.entity.template.structure.field.FieldType;

import java.util.List;

/**
 * Created by seveniu on 7/22/17.
 * *
 */
public class FieldContent {
    private String name;
    private String label;
    private FieldType fieldType;
    private List<Link> links;
    private List<String> contents;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public List<String> getContents() {
        return contents;
    }

    public void setContents(List<String> contents) {
        this.contents = contents;
    }
}
