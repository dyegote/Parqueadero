package com.adnceiba.parking;

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
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.annotation.meta.When;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityEspressoTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private static Matcher<View> childAtPosition(final Matcher<View> parentMatcher, final int position) {

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

    @Test
    public void searchEmpty_invalidLicensePlate_emptyResult() {
        Given:
        clickInputSearch();

        When:
        keypressInputSearch("123456789");

        Then:
        onView(allOf(withText("No se encontraron resultados"))).check(matches(withText("No se encontraron resultados")));
    }

    @Test
    public void enterMoto_fillInputFields_allowEnter() {
        Given:
        onView(withId(R.id.enterVehicleButton)).perform(click());

        When:
        onView(withId(R.id.typeTextInputLayout)).perform(click());
        onView(withText(containsString("MOTO"))).inRoot(isPlatformPopup()).perform(click());
        onView(withId(R.id.cyilinderEditText)).perform(replaceText("500"), closeSoftKeyboard());
        onView(withId(R.id.licensePlateEditText)).perform(typeText("ZXC321"), closeSoftKeyboard());
        onView(withText("ACEPTAR")).perform(click());

        Then:
        onView(anyOf(withText("Vehiculo ingresado."), withText("Hay un vehiculo estacionado con esta placa."))).check(matches(isDisplayed()));
    }

    @Test
    public void enterCar_fillInputFields_allowEnter() {
        Given:
        onView(withId(R.id.enterVehicleButton)).perform(click());

        When:
        onView(withId(R.id.typeTextInputLayout)).perform(click());
        onView(withText(containsString("CARRO"))).inRoot(isPlatformPopup()).perform(click());
        onView(withId(R.id.licensePlateEditText)).perform(typeText("IXC321"), closeSoftKeyboard());
        onView(withText("ACEPTAR")).perform(click());

        Then:
        onView(anyOf(withText("Vehiculo ingresado."), withText("Hay un vehiculo estacionado con esta placa."))).check(matches(isDisplayed()));
    }

    @Test
    public void leaveVehicle_selectActiveVehicle_leaveVehicle() {
        Given:
        onView(withId(R.id.enterVehicleButton)).perform(click());
        onView(withId(R.id.typeTextInputLayout)).perform(click());
        onView(withText(containsString("CARRO"))).inRoot(isPlatformPopup()).perform(click());
        onView(withId(R.id.licensePlateEditText)).perform(typeText("IHZ234"), closeSoftKeyboard());
        onView(withText("ACEPTAR")).perform(click());
        onView(withText("ACEPTAR")).perform(click());
        clickInputSearch();
        keypressInputSearch("IHZ234");

        When:
        onView(allOf(withId(R.id.leavingButton))).perform(click());
        onView(allOf(withId(R.id.licensePlateTextView))).check(matches(withText(endsWith("IHZ234"))));
        onView(withText("ACEPTAR")).perform(click());

        Then:
        onView(withText("Vehiculo salio.")).check(matches(isDisplayed()));
    }

    private void keypressInputSearch(String valueToWrite) {
        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.editTextSearch),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.textInputLayoutSearch),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText(valueToWrite), closeSoftKeyboard());
    }

    private void clickInputSearch() {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.editTextSearch),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.textInputLayoutSearch),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(click());
    }
}
