package exp.glorio.common;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public interface BasePresenter <T> {
    void init(T view);
}
