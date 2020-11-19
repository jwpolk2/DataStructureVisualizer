package com.example.datastructurevisualizer;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.CoordinatesProvider;
import androidx.test.espresso.action.GeneralClickAction;
import androidx.test.espresso.action.Press;
import androidx.test.espresso.action.Tap;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.datastructurevisualizer.ui.Visualizer;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class BST_insert_checkNodes {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void waitFor() {
        try{
            wait(20000);
        }catch (Exception e) {

        }
    }


    @Test
    public void bST_insert_checkNodes() {
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
        appCompatEditText.perform(replaceText("50"), closeSoftKeyboard());

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
        appCompatEditText2.perform(replaceText("56"), closeSoftKeyboard());

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
        appCompatEditText3.perform(replaceText("35"), closeSoftKeyboard());

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

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.input_nodes),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.visualizer_fragment),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("15"), closeSoftKeyboard());

        ViewInteraction appCompatImageButton5 = onView(
                allOf(withId(R.id.button_insert),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.visualizer_fragment),
                                        0),
                                1),
                        isDisplayed()));
        appCompatImageButton5.perform(click());

        try{
            wait(200000);
        }catch (Exception e) {

        }

        Node root = Visualizer.tree.getNode(50);

        clickXY(root.position[0], root.position[1]);

        try{
            wait(200000);
        }catch (Exception e) {

        }

        ViewInteraction textView = onView(
                allOf(withId(R.id.nodeActionLabelValue), withText("Node value: 50"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class))),
                        isDisplayed()));
        textView.check(matches(withText("Node value: 50")));

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.nodeActionDelete), withText("Delete"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                0),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction linearLayout2 = onView(
                allOf(withId(R.id.visualizer_fragment),
                        childAtPosition(
                                allOf(withId(R.id.fragment_container),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        linearLayout2.perform(click());

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.nodeActionLabelValue), withText("Node value: 56"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class))),
                        isDisplayed()));
        textView2.check(matches(withText("Node value: 56")));

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.nodeActionCancel), withText("Cancel"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                1),
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

    public static ViewAction clickXY(final int x, final int y){
        return new GeneralClickAction(
                Tap.SINGLE,
                new CoordinatesProvider() {
                    @Override
                    public float[] calculateCoordinates(View view) {

                        final int[] screenPos = new int[2];
                        view.getLocationOnScreen(screenPos);

                        final float screenX = screenPos[0] + x;
                        final float screenY = screenPos[1] + y;
                        float[] coordinates = {screenX, screenY};

                        return coordinates;
                    }
                },
                Press.FINGER);
    }
}
