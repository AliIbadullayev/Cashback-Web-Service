package com.example.data.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "actors")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlActorsWrapper {

    @XmlElement(name = "actor")
    private List<XmlActor> actors;


    public XmlActorsWrapper(){
        actors = new ArrayList<>();
    }

    public XmlActorsWrapper(List<XmlActor> actors) {
        this.actors = actors;
    }

    public List<XmlActor> getActors() {
        return actors;
    }

    public void setActors(List<XmlActor> actors) {
        this.actors = actors;
    }
}
