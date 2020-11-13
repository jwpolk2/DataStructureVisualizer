package com.example.datastructurevisualizer;


import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class BSTExistsTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void bSTExistsTest() {
        ViewInteraction button = onView(
                allOf(withId(R.id.bstButton), withText("Binary Search Tree"),
                        withParent(withParent(withId(R.id.fragment_container))),
                        isDisplayed()));
        button.check(matches(isDisplayed()));
    }

    @Test
    public void bSTExistsTest2() {
        onView(withId(R.id.bstButton)).perform(click()).check(matches(isDisplayed()));
    }
}
