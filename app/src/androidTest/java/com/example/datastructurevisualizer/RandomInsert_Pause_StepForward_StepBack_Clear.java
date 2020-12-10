package com.example.datastructurevisualizer;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
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

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RandomInsert_Pause_StepForward_StepBack_Clear {

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
    public void randomInsert_Pause_StepForward_StepBack_Clear() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.rbtButton), withText("Red Black Tree"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.TableLayout")),
                                        1),
                                0),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.spinner_tree_options),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.visualizer_fragment),
                                        0),
                                4),
                        isDisplayed()));
        appCompatSpinner.perform(click());

        DataInteraction appCompatTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(3);
        appCompatTextView.perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.button_pause),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.visualizer_fragment),
                                        1),
                                4),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withId(R.id.button_next),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.visualizer_fragment),
                                        1),
                                2),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withId(R.id.button_next),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.visualizer_fragment),
                                        1),
                                2),
                        isDisplayed()));
        appCompatImageButton3.perform(click());

        ViewInteraction appCompatImageButton4 = onView(
                allOf(withId(R.id.button_next),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.visualizer_fragment),
                                        1),
                                2),
                        isDisplayed()));
        appCompatImageButton4.perform(click());

        ViewInteraction appCompatImageButton5 = onView(
                allOf(withId(R.id.button_next),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.visualizer_fragment),
                                        1),
                                2),
                        isDisplayed()));
        appCompatImageButton5.perform(click());

        ViewInteraction appCompatImageButton6 = onView(
                allOf(withId(R.id.button_next),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.visualizer_fragment),
                                        1),
                                2),
                        isDisplayed()));
        appCompatImageButton6.perform(click());

        ViewInteraction appCompatImageButton7 = onView(
                allOf(withId(R.id.button_next),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.visualizer_fragment),
                                        1),
                                2),
                        isDisplayed()));
        appCompatImageButton7.perform(click());

        ViewInteraction appCompatImageButton8 = onView(
                allOf(withId(R.id.button_next),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.visualizer_fragment),
                                        1),
                                2),
                        isDisplayed()));
        appCompatImageButton8.perform(click());

        ViewInteraction appCompatImageButton9 = onView(
                allOf(withId(R.id.button_next),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.visualizer_fragment),
                                        1),
                                2),
                        isDisplayed()));
        appCompatImageButton9.perform(click());

        ViewInteraction appCompatImageButton10 = onView(
                allOf(withId(R.id.button_next),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.visualizer_fragment),
                                        1),
                                2),
                        isDisplayed()));
        appCompatImageButton10.perform(click());

        ViewInteraction appCompatImageButton11 = onView(
                allOf(withId(R.id.button_next),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.visualizer_fragment),
                                        1),
                                2),
                        isDisplayed()));
        appCompatImageButton11.perform(click());

        ViewInteraction appCompatImageButton12 = onView(
                allOf(withId(R.id.button_next),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.visualizer_fragment),
                                        1),
                                2),
                        isDisplayed()));
        appCompatImageButton12.perform(click());

        ViewInteraction appCompatImageButton13 = onView(
                allOf(withId(R.id.button_next),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.visualizer_fragment),
                                        1),
                                2),
                        isDisplayed()));
        appCompatImageButton13.perform(click());

        ViewInteraction appCompatImageButton14 = onView(
                allOf(withId(R.id.button_next),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.visualizer_fragment),
                                        1),
                                2),
                        isDisplayed()));
        appCompatImageButton14.perform(click());

        ViewInteraction appCompatImageButton15 = onView(
                allOf(withId(R.id.button_next),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.visualizer_fragment),
                                        1),
                                2),
                        isDisplayed()));
        appCompatImageButton15.perform(click());

        ViewInteraction appCompatImageButton16 = onView(
                allOf(withId(R.id.button_next),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.visualizer_fragment),
                                        1),
                                2),
                        isDisplayed()));
        appCompatImageButton16.perform(click());

        ViewInteraction appCompatImageButton17 = onView(
                allOf(withId(R.id.button_next),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.visualizer_fragment),
                                        1),
                                2),
                        isDisplayed()));
        appCompatImageButton17.perform(click());

        ViewInteraction appCompatImageButton18 = onView(
                allOf(withId(R.id.button_next),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.visualizer_fragment),
                                        1),
                                2),
                        isDisplayed()));
        appCompatImageButton18.perform(click());

        ViewInteraction appCompatImageButton19 = onView(
                allOf(withId(R.id.button_next),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.visualizer_fragment),
                                        1),
                                2),
                        isDisplayed()));
        appCompatImageButton19.perform(click());

        ViewInteraction appCompatImageButton20 = onView(
                allOf(withId(R.id.button_next),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.visualizer_fragment),
                                        1),
                                2),
                        isDisplayed()));
        appCompatImageButton20.perform(click());

        ViewInteraction appCompatImageButton21 = onView(
                allOf(withId(R.id.button_next),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.visualizer_fragment),
                                        1),
                                2),
                        isDisplayed()));
        appCompatImageButton21.perform(click());

        ViewInteraction appCompatImageButton22 = onView(
                allOf(withId(R.id.button_previous),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.visualizer_fragment),
                                        1),
                                1),
                        isDisplayed()));
        appCompatImageButton22.perform(click());

        ViewInteraction appCompatImageButton23 = onView(
                allOf(withId(R.id.button_previous),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.visualizer_fragment),
                                        1),
                                1),
                        isDisplayed()));
        appCompatImageButton23.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.button_clear), withText("Clear"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout2),
                                        childAtPosition(
                                                withId(R.id.visualizer_fragment),
                                                4)),
                                2),
                        isDisplayed()));
        appCompatButton2.perform(click());
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
