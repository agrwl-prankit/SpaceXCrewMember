package com.prankit.spacexcrewmember.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "CrewMember")
public class CrewMemberRoomModel {

    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "agency")
    private String agency;
    @ColumnInfo(name = "status")
    private String status;
    @ColumnInfo(name = "wikipedia")
    private String wikipedia;
    @ColumnInfo(name = "image")
    private String image;

    public CrewMemberRoomModel(String name, String agency, String status, String wikipedia, String image) {
        this.name = name;
        this.agency = agency;
        this.status = status;
        this.wikipedia = wikipedia;
        this.image = image;
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

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWikipedia() {
        return wikipedia;
    }

    public void setWikipedia(String wikipedia) {
        this.wikipedia = wikipedia;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

