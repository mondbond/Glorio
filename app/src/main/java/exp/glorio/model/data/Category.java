package exp.glorio.model.data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Category {


    @Id(autoincrement = true)
    Long id;

    @Unique
    String categoryName;

    boolean isDelete;

    @Generated(hash = 10239639)
    public Category(Long id, String categoryName, boolean isDelete) {
        this.id = id;
        this.categoryName = categoryName;
        this.isDelete = isDelete;
    }

    @Generated(hash = 1150634039)
    public Category() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public boolean getIsDelete() {
        return this.isDelete;
    }

    public void setIsDelete(boolean isDelete) {
        this.isDelete = isDelete;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
