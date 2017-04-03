package exp.glorio.model.PostPOJO;

/**
 * Created by User on 16.03.2017.
 */

public abstract class Attachments {

    public static final String PHOTO_TYPE = "photo";
    public static final String LINK_TYPE = "link";

    protected String type;

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
}
