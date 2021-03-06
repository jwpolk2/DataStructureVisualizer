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
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class Full_PostOrder {

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
    public void full_PostOrder() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.filesButton), withText("Files / Load Structure"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.TableLayout")),
                                        4),
                                0),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.filesRecycle),
                        childAtPosition(
                                withClassName(is("android.widget.ScrollView")),
                                0)));
        recyclerView.perform(actionOnItemAtPosition(3, click()));

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.fileDialog_loadBtn), withText("Load"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        0),
                                3),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.spinner_traversal),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.visualizer_fragment),
                                        1),
                                0),
                        isDisplayed()));
        appCompatSpinner.perform(click());

        DataInteraction appCompatCheckedTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(2);
        appCompatCheckedTextView.perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.button_play),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.visualizer_fragment),
                                        1),
                                3),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.button_clear), withText("Clear"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout2),
                                        childAtPosition(
                                                withId(R.id.visualizer_fragment),
                                                4)),
                                2),
                        isDisplayed()));
        appCompatButton3.perform(click());
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
