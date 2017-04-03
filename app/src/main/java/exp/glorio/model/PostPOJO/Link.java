package exp.glorio.model.PostPOJO;

/**
 * Created by User on 16.03.2017.
 */

public class Link extends Attachments {

    private String url;
    private String title;
    private String description;
    private String image_src;
//    PhotoLink photoLink;

    public Link(String type, String url, String title, String description, String image_src) {
        this.type = type;
        this.url = url;
        this.title = title;
        this.description = description;
        this.image_src = image_src;
    }


//    public Link(String type, String url, String title, String description, PhotoLink photoLink) {
//        this.type = type;
//        this.url = url;
//        this.title = title;
//        this.description = description;
//        this.photoLink = photoLink;
//    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public PhotoLink getPhotoLink() {
//        return photoLink;
//    }
//
//    public void setPhotoLink(PhotoLink photoLink) {
//        this.photoLink = photoLink;
//    }


    public String getImageSrc() {
        return image_src;
    }

    public void setImageSrc(String image_src) {
        this.image_src = image_src;
    }
}
