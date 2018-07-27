package leonardolana.poppicture.data;

/**
 * Created by leonardolana on 7/25/18.
 * HomeSection is the bridge between the presenter and the view.
 */

public enum HomeSection {

    NEARBY("leonardolana.poppicture.home.nearby.HomeNearbyFragment", "action_nearby"),
    LIKED("leonardolana.poppicture.home.liked.HomeLikedFragment", "action_liked"),
    PROFILE("leonardolana.poppicture.profile.ProfileFragment", "action_profile");

    private final String mName;
    private final String mMenu;

    HomeSection(String name, String menu) {
        mName = name;
        mMenu = menu;
    }

    public String getName() {
        return mName;
    }

    public String getMenu() {
        return mMenu;
    }

}
