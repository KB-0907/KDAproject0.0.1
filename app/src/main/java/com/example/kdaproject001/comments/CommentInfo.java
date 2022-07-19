package com.example.kdaproject001.comments;

public class CommentInfo {
    //받을 것 : 댓글 내용(comment), 댓글 작성자(commenter), 댓글을 작성한 게시글(comPostID), 댓글 작성 시간(comCreated)
    String comment, commenter, comPostID;
    long comCreated;

    public CommentInfo(String comment, String commenter, String comPostID, long comCreated){
        this.comment = comment;
        this.commenter = commenter;
        this.comPostID = comPostID;
        this.comCreated = comCreated;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommenter() {
        return commenter;
    }

    public void setCommenter(String commenter) {
        this.commenter = commenter;
    }

    public String getComPostID() {
        return comPostID;
    }

    public void setComPostID(String comPostID) {
        this.comPostID = comPostID;
    }

    public long getComCreated() {
        return comCreated;
    }

    public void setComCreated(long comCreated) {
        this.comCreated = comCreated;
    }
}
