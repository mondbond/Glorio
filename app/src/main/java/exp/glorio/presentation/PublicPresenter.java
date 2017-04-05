package exp.glorio.presentation;

import com.vk.sdk.api.model.VKList;
import org.json.JSONException;
import java.util.ArrayList;
import javax.inject.Inject;
import exp.glorio.common.BasePresenter;
import exp.glorio.model.DbRepository;
import exp.glorio.model.data.Public;
import exp.glorio.network.VkApiNetwork;
import exp.glorio.view.fragments.PublicView;

public class PublicPresenter implements BasePresenter<PublicView>{

    private PublicView mView;
    private DbRepository mRepository;
    private VkApiNetwork mVkApiNetwork;

    @Inject
    public PublicPresenter(DbRepository repository, VkApiNetwork vk) {
        this.mRepository = repository;
        this.mVkApiNetwork = vk;
    }

    @Override
    public void init(PublicView view) {
        this.mView = view;
    }

    public void getAllPublicsByCategoryId(Long categoryId) {
        mRepository.getPublicsByCategoryId(categoryId).subscribe(result -> {
            getPublicInfoById(result);
        });
    }

    public void savePublic(VKList list, int selectedItem, long categoryId) {
        try {
            int pubId = list.get(selectedItem).fields.getInt("id");
            mRepository.savePublic(pubId, categoryId).subscribe(confirm -> {
                mView.showAllPublics();
            });
        }catch (JSONException e){}
    }

    public void getPublicInfoById(ArrayList<Public> list) {
        StringBuilder ids = new StringBuilder();
        for (int i = 0; i != list.size(); i++) {
            ids.append(Integer.toString(list.get(i).getPublicId()));
            if(i != list.size() -1) {
                ids.append(",");
            }
        }
        if(!ids.toString().equals("")) {
            mVkApiNetwork.getPublicsInfoByIds(ids.toString()).subscribe(response -> {
                if (response != null) {
                    VKList vkList = (VKList) response.parsedModel;
                    mView.showAllPublics(vkList);
                }
            });
        }
    }

    public void deleteSelectedItems(ArrayList<Long> checkedItems) {
        mRepository.deleteSelectedPublics(checkedItems).subscribe(res ->{
            if(res) {
                mView.refresh();
            }
        });
    }
}
