package exp.glorio.model.PostPOJO;

import java.util.ArrayList;

public class Post {
    private int id;
    private int isAd;
    private String type;
    private String text;

    private String index;

    private ArrayList<Attachments> attachmentses;

    private int likes;
    private int reposts;

    private int groupId;
    private String groupName;
    private int isMember;
    private String photo100Url;

    private long date;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int isAd() {
        return isAd;
    }

    public void setAd(int ad) {
        isAd = ad;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public ArrayList<Attachments> getAttachmentses() {
        return attachmentses;
    }

    public void setAttachmentses(ArrayList<Attachments> attachmentses) {
        this.attachmentses = attachmentses;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getReposts() {
        return reposts;
    }

    public void setReposts(int reposts) {
        this.reposts = reposts;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int isMember() {
        return isMember;
    }

    public void setMember(int member) {
        isMember = member;
    }

    public String getGroupLogo100Url() {
        return photo100Url;
    }

    public void setPhoto100Url(String photo100Url) {
        this.photo100Url = photo100Url;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
