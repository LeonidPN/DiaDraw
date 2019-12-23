package com.example.diadraw;

import android.content.Context;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.diadraw.Models.FileService;
import com.example.diadraw.Models.WorkModels.FileModel;
import com.example.diadraw.Views.ChooseFileActivity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static androidx.test.espresso.Espresso.onView;

import androidx.test.espresso.contrib.RecyclerViewActions;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4ClassRunner.class)
public class AddingSchemeElementTest {

    private String stringFileName;

    @Rule
    public ActivityTestRule<ChooseFileActivity> activityTestRule = new ActivityTestRule<>(ChooseFileActivity.class);

    @Before
    public void initString() {
        stringFileName = "test";
    }

    @Test
    public void addSchemeElements() throws IOException {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        FileService fileService = new FileService();

        FileModel fileModel = fileService.getFile(context, stringFileName);

        int startCount = fileModel.getFigures().size();

        onView(withId(R.id.recyclerVewChooseFile))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,
                        click()));
        onView(withId(R.id.floatingActionButtonFigures))
                .perform(click());
        onView(withId(R.id.imageViewFigureStart))
                .perform(click());

        onView(withId(R.id.floatingActionButtonFigures))
                .perform(click());
        onView(withId(R.id.imageViewFigureEnd))
                .perform(click());

        onView(withId(R.id.floatingActionButtonFigures))
                .perform(click());
        onView(withId(R.id.imageViewFigureActivity))
                .perform(click());

        onView(withId(R.id.floatingActionButtonFigures))
                .perform(click());
        onView(withId(R.id.imageViewFigureInput))
                .perform(click());

        onView(withId(R.id.floatingActionButtonFigures))
                .perform(click());
        onView(withId(R.id.imageViewFigureOutput))
                .perform(click());

        onView(withId(R.id.floatingActionButtonFigures))
                .perform(click());
        onView(withId(R.id.imageViewFigureCondition))
                .perform(click());

        onView(withId(R.id.floatingActionButtonFigures))
                .perform(click());
        onView(withId(R.id.imageViewFigureCycleStart))
                .perform(click());

        onView(withId(R.id.floatingActionButtonFigures))
                .perform(click());
        onView(withId(R.id.imageViewFigureCycleEnd))
                .perform(click());

        onView(withId(R.id.floatingActionButtonSave))
                .perform(click());

        fileModel = fileService.getFile(context, stringFileName);

        int endCount = fileModel.getFigures().size();

        Assert.assertEquals(startCount + 8, endCount);
    }

}
