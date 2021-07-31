package com.example.lamzone2;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.lamzone2", appContext.getPackageName());
    }
    @Test
    public void Reunion_List_NotEmpty(){
        onView(allOf(ViewMatchers.withId(R.id.RecyclerView), isDisplayed()))
                .check(matches(ViewMatchers.hasMinimumChildCount(1)));
    }




    @Test
    public void addReunion(){
         ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.reunion_add),
                        isDisplayed()));
        textInputEditText.perform(replaceText("test"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.timeButton), withText("open time picker"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        0),
                                3),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction materialButton2 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton2.perform(scrollTo(), click());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.sujet_add),
                        isDisplayed()));
        textInputEditText2.perform(replaceText("test"), closeSoftKeyboard());

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.email_add),
                        isDisplayed()));
        textInputEditText3.perform(replaceText("test"), closeSoftKeyboard());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.create), withText("Save"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        0),
                                6),
                        isDisplayed()));
        materialButton3.perform(click());


        onView(allOf(ViewMatchers.withId(R.id.RecyclerView), isDisplayed())).check(matches(isDisplayed()));
    }


    @Test
    public void TestFilter(){
        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.action_search), withContentDescription("Search"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction searchAutoComplete = onView(
                allOf(withId(R.id.search_src_text),
                        childAtPosition(
                                allOf(withId(R.id.search_plate),
                                        childAtPosition(
                                                withId(R.id.search_edit_frame),
                                                1)),
                                0),
                        isDisplayed()));
        searchAutoComplete.perform(replaceText("peach"), closeSoftKeyboard());
        onView(allOf(withId(R.id.sujet))).check(matches(withText("Peach")));

    }

    @Test
    public void deleteReunion(){
        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.delete_bouton),
                        childAtPosition(
                                allOf(withId(R.id.parent),
                                        childAtPosition(
                                                withId(R.id.RecyclerView),
                                                0)),
                                7),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.sujet), withText("Peach"),
                        withParent(allOf(withId(R.id.parent),
                                withParent(withId(R.id.RecyclerView)))),
                        isDisplayed()));
        textView.check(doesNotExist());


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