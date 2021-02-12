
package com.openclassrooms.entrevoisins.neighbour_list;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static com.openclassrooms.entrevoisins.utils.WaitViewAction.waitFor;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.IsNull.notNullValue;



/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // This is fixed
    private static int ITEMS_COUNT = 12;

    private ListNeighbourActivity mActivity;

    private List<Neighbour> mNeighbourList;

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        mNeighbourList = DI.getNeighbourApiService().getNeighbours();
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        onView(allOf(withId(R.id.list_neighbours), isDisplayed())).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(allOf(withId(R.id.list_neighbours), isDisplayed())).check(withItemCount(ITEMS_COUNT-1));
    }

    @Test
    public void myNeighboursList_favoriteAction_shouldAddItem() {
        onView(withId(R.id.container)).perform(swipeLeft());
        onView(isRoot()).perform(waitFor(500));
        onView(allOf(withId(R.id.list_neighbours), isDisplayed())).check(withItemCount(0));
        onView(withId(R.id.container)).perform(swipeRight());
        onView(isRoot()).perform(waitFor(500));
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(isRoot()).perform(waitFor(500));
        onView(withId(R.id.favButton)).perform(click());
        pressBack();
        onView(withId(R.id.container)).perform(swipeLeft());
        onView(isRoot()).perform(waitFor(500));
        onView(allOf(withId(R.id.list_neighbours), isDisplayed())).check(withItemCount(1));

        onView(withId(R.id.container)).perform(swipeRight());
        onView(isRoot()).perform(waitFor(500));
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(isRoot()).perform(waitFor(500));
        onView(withId(R.id.favButton)).perform(click());
        pressBack();
        onView(withId(R.id.container)).perform(swipeLeft());
        onView(isRoot()).perform(waitFor(500));
        onView(allOf(
                withId(R.id.list_neighbours), isDisplayed())).check(withItemCount(0));
    }

    @Test
    public void myNeighbourDetailsActivity_retrieveAction_shouldShowData() {
        Neighbour firstNeighbour = mNeighbourList.get(0);
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.textNameOnPicture)).check(matches(withText(firstNeighbour.getName())));
        onView(withId(R.id.textName)).check(matches(withText(firstNeighbour.getName())));
        onView(withId(R.id.textAddress)).check(matches(withText(firstNeighbour.getAddress())));
        onView(withId(R.id.textPhoneNumber)).check(matches(withText(firstNeighbour.getPhoneNumber())));
        onView(withId(R.id.textSocial)).check(matches(withText("www.facebook.fr/" + firstNeighbour.getName().toLowerCase())));
        onView(withId(R.id.textAboutMe)).check(matches(withText(firstNeighbour.getAboutMe())));
    }

}