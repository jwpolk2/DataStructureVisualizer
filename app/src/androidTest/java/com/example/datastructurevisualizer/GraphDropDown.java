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
import org.hamcrest.core.IsInstanceOf;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class GraphDropDown {

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
    public void testDropDownTextOnOpeningGraphVisualizer() {

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.graphButton), withText("Graphs"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.TableLayout")),
                                        3),
                                0),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(android.R.id.text1), withText("Select Traversal"),
                        withParent(allOf(withId(R.id.spinner_traversal),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        textView.check(matches(withText("Select Traversal")));

    }

    @Test
    public void testSelectingPrimsFromDropDown() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.graphButton), withText("Graphs"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.TableLayout")),
                                        3),
                                0),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.spinner_traversal),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatSpinner.perform(click());

        DataInteraction appCompatCheckedTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(1);
        appCompatCheckedTextView.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(android.R.id.text1), withText("Prim's MST"),
                        withParent(allOf(withId(R.id.spinner_traversal),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        textView.check(matches(withText("Prim's MST")));
    }



    @Test
    public void testSelectingDijkstrasFromDropDown() {

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.graphButton), withText("Graphs"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.TableLayout")),
                                        3),
                                0),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatSpinner2 = onView(
                allOf(withId(R.id.spinner_traversal),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatSpinner2.perform(click());

        DataInteraction appCompatCheckedTextView2 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(2);
        appCompatCheckedTextView2.perform(click());

        ViewInteraction textView2 = onView(
                allOf(withId(android.R.id.text1), withText("Dijkstra's Shortest Path"),
                        withParent(allOf(withId(R.id.spinner_traversal),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        textView2.check(matches(withText("Dijkstra's Shortest Path")));

    }

    @Test
    public void testSelectingKruskalsMSTFromDropDown() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.graphButton), withText("Graphs"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.TableLayout")),
                                        3),
                                0),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatSpinner3 = onView(
                allOf(withId(R.id.spinner_traversal),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatSpinner3.perform(click());

        DataInteraction appCompatCheckedTextView3 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(3);
        appCompatCheckedTextView3.perform(click());

        ViewInteraction textView2 = onView(
                allOf(withId(android.R.id.text1), withText("Kruskal's MST"),
                        withParent(allOf(withId(R.id.spinner_traversal),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        textView2.check(matches(withText("Kruskal's MST")));

        ViewInteraction appCompatSpinner4 = onView(
                allOf(withId(R.id.spinner_traversal),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatSpinner4.perform(click());

    }

    @Test
    public void testSelectingBreadthFirstPathFromDropDown() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.graphButton), withText("Graphs"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.TableLayout")),
                                        3),
                                0),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatSpinner3 = onView(
                allOf(withId(R.id.spinner_traversal),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatSpinner3.perform(click());

        DataInteraction appCompatCheckedTextView3 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(4);
        appCompatCheckedTextView3.perform(click());

        ViewInteraction textView3 = onView(
                allOf(withId(android.R.id.text1), withText("Breadth-First Path"),
                        withParent(allOf(withId(R.id.spinner_traversal),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        textView3.check(matches(withText("Breadth-First Path")));


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
