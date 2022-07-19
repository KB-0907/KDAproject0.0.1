package com.example.kdaproject001.board;

import java.util.ArrayList;

public class PostInfo {
    private String title;
    private String content;
    private String publisher;
    private String postID;
    private long created;

    public PostInfo(String title, String content, String publisher, String postID, long created){
        this.title = title;
        this.content = content;
        this.publisher = publisher;
        this.postID = postID;
        this.created = created;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }
}
