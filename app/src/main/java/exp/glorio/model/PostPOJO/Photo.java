package exp.glorio.model.PostPOJO;

/**
 * Created by User on 16.03.2017.
 */

public class Photo extends Attachments {
    private int id;
    private String photo604;

    private int width;
    private int height;

    private String text;

    public Photo(String type, int id, String photo604, int width, int height, String text) {
        this.type = type;
        this.id = id;
        this.photo604 = photo604;
        this.width = width;
        this.height = height;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoto604() {
        return photo604;
    }

    public void setPhoto604(String photo604) {
        this.photo604 = photo604;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
