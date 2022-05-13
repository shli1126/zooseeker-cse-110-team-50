package com.example.cse110_team49;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddtoListTest {
    private ExhibitDao dao;
    private ExhibitDatabase db;

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = ExhibitDatabase.getSingleton(context);
        dao = db.exhibitDao();
        List<Exhibit> lst = dao.getAll();
        for (Exhibit e : lst) {
            dao.delete(e);
        }
    }

    @After
    public void clearDb() {
        List<Exhibit> lst = dao.getAll();
        for (Exhibit e : lst) {
            dao.delete(e);
        }
    }


    @Test
    public void addtoListTest() {
        int itemCount = 0;
        ViewInteraction materialAutoCompleteTextView = onView(
                allOf(withId(R.id.searchInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialAutoCompleteTextView.perform(replaceText("mammal"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.searchButton), withText("Search"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton.perform(click());


        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.add_animal), withText("Add"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.animal_items),
                                        0),
                                1),
                        isDisplayed()));
        materialButton2.perform(click());
        itemCount++;

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.add_animal), withText("Add"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.animal_items),
                                        1),
                                1),
                        isDisplayed()));
        materialButton3.perform(click());
        itemCount++;

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.add_animal), withText("Add"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.animal_items),
                                        3),
                                1),
                        isDisplayed()));
        materialButton4.perform(click());
        itemCount++;

        ViewInteraction materialButton5 = onView(
                allOf(withId(R.id.back_button), withText("Back"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton5.perform(click());



        ViewInteraction materialButton6 = onView(
                allOf(withId(R.id.myList), withText("LIST"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton6.perform(click());

        ViewInteraction materialButton7 = onView(
                allOf(withId(R.id.return_button), withText("Back"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton7.perform(click());

        assertEquals(itemCount, 3);

    }


    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }


}
