package com.applepluot.todoapp;


import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.logging.Logger;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    Logger logger = Logger.getLogger(this.getClass().getName());
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void addAndDeleteTest() {
        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.etAddItemText), isDisplayed()));
        appCompatEditText2.perform(replaceText("item 1"));

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.btnAdd), withText("Add"), withContentDescription("Add Button"), isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(android.R.id.text1), withText("item 1"),
                        withParent(withId(R.id.lvItems)),
                        isDisplayed()));
        textView.check(matches(withText("item 1")));
//        ViewInteraction appCompatTextView = onView(
//                allOf(withId(android.R.id.text1), withText("item 1"),
//                        childAtPosition(withId(R.id.lvItems), 0),
//                        isDisplayed()));
        ViewInteraction view = onView(allOf(withParent(withId(R.id.lvItems)),
                ToDoItemsMatcher.withText(Matchers.containsString("item 1"))));
        logger.info(view.toString());
        view.perform(longClick());
        onView(allOf(withId(android.R.id.text1), withText("item 1"))).check(ViewAssertions.doesNotExist());
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
