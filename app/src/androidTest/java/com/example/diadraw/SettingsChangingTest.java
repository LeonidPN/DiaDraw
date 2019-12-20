package com.example.diadraw;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.diadraw.Views.SettingsActivity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4ClassRunner.class)
public class SettingsChangingTest {

    private String stringToType;

    @Rule
    public ActivityTestRule<SettingsActivity> activityTestRule = new ActivityTestRule<>(SettingsActivity.class);

    @Before
    public void initString() {
        stringToType = "4";
    }

    @Test
    public void changeTimeAutoSave() {
        onView(withId(R.id.editTextAutoSaveTime))
                .perform(clearText())
                .perform(typeText(stringToType), closeSoftKeyboard());
        onView(withId(R.id.buttonSave))
                .perform(click());

        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        int time = sharedPreferences.getInt("autoSaveTime", 0);

        Assert.assertEquals(Integer.parseInt(stringToType), time);
    }

}
