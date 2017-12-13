package com.archi.archiinfo.journey.model;

import java.io.Serializable;

/**
 * Created by archi_info on 1/5/2017.
 */

public class ArticleDetail implements Serializable {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String name;
    private String description;
    private String dateTime;
    private String image;
    private String video;
    private String add_image;
    private String Other_image;
    private String like;
    private String dislike;

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getDislike() {
        return dislike;
    }

    public void setDislike(String dislike) {
        this.dislike = dislike;
    }

    public String getOther_image() {
        return Other_image;
    }

    public void setOther_image(String other_image) {
        Other_image = other_image;
    }

    private String authorName;

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }



    public String getAdd_image() {
        return add_image;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setAdd_image(String add_image) {
        this.add_image = add_image;
    }

    public String getName() {
        return name;

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
