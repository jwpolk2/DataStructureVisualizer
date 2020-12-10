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
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class Save_Rename_DeleteFile {

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
    public void save_Rename_DeleteFile() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.avlButton), withText("AVL Tree"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.TableLayout")),
                                        2),
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

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.button_save), withText("Save"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout2),
                                        childAtPosition(
                                                withId(R.id.visualizer_fragment),
                                                4)),
                                0),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.saveDialog_fileName),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("test"), closeSoftKeyboard());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.saveDialog_saveBtn), withText("Save"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        0),
                                1),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.button_load), withText("Load"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout2),
                                        childAtPosition(
                                                withId(R.id.visualizer_fragment),
                                                4)),
                                1),
                        isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.filesRecycle),
                        childAtPosition(
                                withClassName(is("android.widget.ScrollView")),
                                0)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.fileDialog_renameBtn), withText("Rename"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        0),
                                1),
                        isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.renameEditText),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("test2"), closeSoftKeyboard());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.renameConfirmBtn), withText("Rename"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                0),
                        isDisplayed()));
        appCompatButton6.perform(click());

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.filesRecycle),
                        childAtPosition(
                                withClassName(is("android.widget.ScrollView")),
                                0)));
        recyclerView2.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction appCompatButton7 = onView(
                allOf(withId(R.id.fileDialog_deleteBtn), withText("Delete"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton7.perform(click());

        ViewInteraction appCompatButton8 = onView(
                allOf(withId(R.id.fileFinalCancel), withText("Cancel"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                1),
                        isDisplayed()));
        appCompatButton8.perform(click());

        ViewInteraction appCompatButton9 = onView(
                allOf(withId(R.id.fileDialog_deleteBtn), withText("Delete"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton9.perform(click());

        ViewInteraction appCompatButton10 = onView(
                allOf(withId(R.id.fileFinalDelete), withText("Delete"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                0),
                        isDisplayed()));
        appCompatButton10.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.fileViewName), withText("default_fullTree"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class))),
                        isDisplayed()));
        textView.check(matches(withText("default_fullTree")));
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
