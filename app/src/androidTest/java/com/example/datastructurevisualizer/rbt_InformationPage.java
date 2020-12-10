package com.example.datastructurevisualizer;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class rbt_InformationPage {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    @After
    public void waitForAfter() {
        try{
            wait(20000);
        }catch (Exception e) {

        }
    }

    @Before
    public void waitForBefore() {
        try{
            wait(20000);
        }catch (Exception e) {

        }
    }
    @Test
    public void rbt_InformationPage() {
        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.rbtInfoButton),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.TableLayout")),
                                        1),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction textView = onView(
                allOf(withText("Red Black Tree"),
                        withParent(allOf(withId(R.id.action_bar),
                                withParent(withId(R.id.action_bar_container)))),
                        isDisplayed()));
        textView.check(matches(withText("Red Black Tree")));

        ViewInteraction textView2 = onView(
                allOf(withText("Overview"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class))),
                        isDisplayed()));
        textView2.check(matches(withText("Overview")));

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.button_visualize), withText("Visualize"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                17)));
        appCompatButton.perform(scrollTo(), click());

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withId(R.id.button_info),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout2),
                                        childAtPosition(
                                                withId(R.id.visualizer_fragment),
                                                4)),
                                3),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction tabView = onView(
                allOf(withContentDescription("Complexities"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.informationTabs),
                                        0),
                                1),
                        isDisplayed()));
        tabView.perform(click());

        ViewInteraction textView3 = onView(
                allOf(withText("Complexities"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class))),
                        isDisplayed()));
        textView3.check(matches(withText("Complexities")));

        ViewInteraction tabView2 = onView(
                allOf(withContentDescription("Code"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.informationTabs),
                                        0),
                                2),
                        isDisplayed()));
        tabView2.perform(click());

        ViewInteraction textView4 = onView(
                allOf(withText("Code"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class))),
                        isDisplayed()));
        textView4.check(matches(withText("Code")));

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.button_visualize), withText("Visualize"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                120)));
        appCompatButton2.perform(scrollTo(), click());
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
