package com.example.datastructurevisualizer;


import android.view.View;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.core.IsInstanceOf;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CheckHomeFragmentViewItems {

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
    public void testBSTButtonOnHomeFragment() {
        ViewInteraction button = onView(
                allOf(withId(R.id.bstButton), withText("Binary Search Tree"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.TableLayout.class))),
                        isDisplayed()));
        button.check(matches(isDisplayed()));
    }

    @Test
    public void testRBTButtonOnHomeFragment() {
        ViewInteraction button2 = onView(
                allOf(withId(R.id.rbtButton), withText("Red Black Tree"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.TableLayout.class))),
                        isDisplayed()));
        button2.check(matches(isDisplayed()));
    }

    @Test
    public void testAVLButtonOnHomeFragment() {
        ViewInteraction button3 = onView(
                allOf(withId(R.id.avlButton), withText("AVL Tree"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.TableLayout.class))),
                        isDisplayed()));
        button3.check(matches(isDisplayed()));
    }


    @Test
    public void testGraphButtonOnHomeFragment() {
        ViewInteraction button5 = onView(
                allOf(withId(R.id.graphButton), withText("Weighted Graphs"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.TableLayout.class))),
                        isDisplayed()));
        button5.check(matches(isDisplayed()));
    }

    @Test
    public void testFilesButtonOnHomeFragment() {
        ViewInteraction button6 = onView(
                allOf(withId(R.id.filesButton), withText("Files / Load Structure"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.TableLayout.class))),
                        isDisplayed()));
        button6.check(matches(isDisplayed()));
    }

    @Test
    public void testBSTInfoButtonOnHomeFragment() {
        ViewInteraction imageButton = onView(
                allOf(withId(R.id.bstInfoButton),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.TableLayout.class))),
                        isDisplayed()));
        imageButton.check(matches(isDisplayed()));
    }

    @Test
    public void testRBTInfoButtonOnHomeFragment() {
        ViewInteraction imageButton2 = onView(
                allOf(withId(R.id.rbtInfoButton),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.TableLayout.class))),
                        isDisplayed()));
        imageButton2.check(matches(isDisplayed()));
    }

    @Test
    public void testAVLInfoButtonOnHomeFragment() {
        ViewInteraction imageButton3 = onView(
                allOf(withId(R.id.avlInfoButton),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.TableLayout.class))),
                        isDisplayed()));
        imageButton3.check(matches(isDisplayed()));
    }

    @Test
    public void testGraphInfoButtonOnHomeFragment() {
        ViewInteraction imageButton4 = onView(
                allOf(withId(R.id.graphInfoButton),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.TableLayout.class))),
                        isDisplayed()));
        imageButton4.check(matches(isDisplayed()));
    }
}

