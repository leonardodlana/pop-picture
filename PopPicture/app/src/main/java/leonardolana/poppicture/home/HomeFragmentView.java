package leonardolana.poppicture.home;

import leonardolana.poppicture.data.HomeSection;

/**
 * Created by leonardolana on 7/20/18.
 */

public interface HomeFragmentView {

    void setSections(HomeSection[] homeSections);

    void onClickSection(HomeSection homeSection);

    void showOnboarding();

}
