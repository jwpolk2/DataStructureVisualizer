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
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class Insert_5_66_5_25 {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @After
    public void closeActivity() {
        try {
            wait(1000);
        }catch (Exception e) {

        }
        mActivityTestRule.finishActivity();
    }

    @Test
    public void insert_5_66_5_25() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.bstButton), withText("Binary Search Tree"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.TableLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.input_nodes),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.visualizer_fragment),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("5"), closeSoftKeyboard());

        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.button_insert),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.visualizer_fragment),
                                        0),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.input_nodes),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.visualizer_fragment),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("66"), closeSoftKeyboard());

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withId(R.id.button_insert),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.visualizer_fragment),
                                        0),
                                1),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.input_nodes),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.visualizer_fragment),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("5"), closeSoftKeyboard());

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withId(R.id.button_insert),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.visualizer_fragment),
                                        0),
                                1),
                        isDisplayed()));
        appCompatImageButton3.perform(click());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.input_nodes),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.visualizer_fragment),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("25"), closeSoftKeyboard());

        ViewInteraction appCompatImageButton4 = onView(
                allOf(withId(R.id.button_insert),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.visualizer_fragment),
                                        0),
                                1),
                        isDisplayed()));
        appCompatImageButton4.perform(click());

        pressBack();

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
