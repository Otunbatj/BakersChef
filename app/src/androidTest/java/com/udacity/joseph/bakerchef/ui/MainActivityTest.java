package com.udacity.joseph.bakerchef.ui;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.udacity.joseph.bakerchef.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        ViewInteraction textView = onView(
                allOf(withId(R.id.action_reload), withContentDescription("Reload"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        textView.check(matches(isDisplayed()));

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.cake_list),
                        withParent(withId(R.id.frameLayout)),
                        isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.cake_list),
                        withParent(withId(R.id.frameLayout)),
                        isDisplayed()));
        recyclerView2.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction appCompatTextView = onView(
                withId(R.id.cakeTitle));
        appCompatTextView.perform(scrollTo(), replaceText("- Graham Cracker crumbs (2.0 CUP)\n- unsalted butter, melted (6.0 TBLSP)\n- granulated sugar (0.5 CUP)\n- salt (1.5 TSP)\n- vanilla (5.0 TBLSP)\n- Nutella or other chocolate-hazelnut spread (1.0 K)\n- Mascapone Cheese(room temperature) (500.0 G)\n- heavy cream(cold) (1.0 CUP)\n- cream cheese(softened) (4.0 OZ)\n"), closeSoftKeyboard());

        ViewInteraction appCompatTextView2 = onView(
                withId(R.id.cakeTitle));
        appCompatTextView2.perform(scrollTo(), replaceText("- Graham Cracker crumbs (2.0 CUP)\n- unsalted butter, melted (6.0 TBLSP)\n- granulated sugar (0.5 CUP)\n- salt (1.5 TSP)\n- vanilla (5.0 TBLSP)\n- Nutella or other chocolate-hazelnut spread (1.0 K)\n- Mascapone Cheese(room temperature) (500.0 G)\n- heavy cream(cold) (1.0 CUP)\n- cream cheese(softened) (4.0 OZ)\n"), closeSoftKeyboard());

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.add_favorite), withContentDescription("Favorite"), isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction actionMenuItemView2 = onView(
                allOf(withId(R.id.remove_favorite), withContentDescription("Stars"), isDisplayed()));
        actionMenuItemView2.perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Navigate up"),
                        withParent(allOf(withId(R.id.action_bar),
                                withParent(withId(R.id.action_bar_container)))),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withContentDescription("Navigate up"),
                        withParent(allOf(withId(R.id.action_bar),
                                withParent(withId(R.id.action_bar_container)))),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction actionMenuItemView3 = onView(
                allOf(withId(R.id.action_about), withContentDescription("About"), isDisplayed()));
        actionMenuItemView3.perform(click());

        ViewInteraction appCompatButton = onView(
                allOf(withId(android.R.id.button1), withText("OK")));
        appCompatButton.perform(scrollTo(), click());

        ViewInteraction actionMenuItemView4 = onView(
                allOf(withId(R.id.action_about), withContentDescription("About"), isDisplayed()));
        actionMenuItemView4.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(android.R.id.button2), withText("Get Code")));
        appCompatButton2.perform(scrollTo(), click());

        pressBack();

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
