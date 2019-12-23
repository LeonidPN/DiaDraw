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
import java.util.ArrayList;
import java.util.Random;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4ClassRunner.class)
public class AddingFileTest {

    private String stringToTypeCorrect;

    private String stringToTypeIncorrect;

    @Rule
    public ActivityTestRule<ChooseFileActivity> activityTestRule = new ActivityTestRule<>(ChooseFileActivity.class);

    @Before
    public void initString() {
        Random random = new Random();
        int rand = random.nextInt() - random.nextInt() + 66 * random.nextInt() - 3 * random.nextInt();
        stringToTypeCorrect = "test" + rand;
        if (stringToTypeCorrect.length() > 20) {
            stringToTypeCorrect = stringToTypeCorrect.substring(0, 19);
        }
        stringToTypeIncorrect = "!test";
    }

    @Test
    public void createFileWithCorrectName() throws IOException {
        onView(withId(R.id.buttonCreateFile))
                .perform(click());
        onView(withId(R.id.editTextFileName))
                .perform(typeText(stringToTypeCorrect), closeSoftKeyboard());
        onView(withId(R.id.buttonCreate))
                .perform(click());

        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        FileService fileService = new FileService();
        ArrayList<FileModel> list = (ArrayList<FileModel>) fileService.getFilesList(context);

        boolean flag = false;
        for (FileModel fileModel : list) {
            if (fileModel.getName().equals(stringToTypeCorrect)) {
                flag = true;
                break;
            }
        }

        Assert.assertTrue(flag);
    }

    @Test
    public void createFileWithIncorrectName() throws IOException {
        onView(withId(R.id.buttonCreateFile))
                .perform(click());
        onView(withId(R.id.editTextFileName))
                .perform(typeText(stringToTypeIncorrect), closeSoftKeyboard());
        onView(withId(R.id.buttonCreate))
                .perform(click());

        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        FileService fileService = new FileService();
        ArrayList<FileModel> list = (ArrayList<FileModel>) fileService.getFilesList(context);

        boolean flag = false;
        for (FileModel fileModel : list) {
            if (fileModel.getName().equals(stringToTypeIncorrect)) {
                flag = true;
                break;
            }
        }

        Assert.assertFalse(flag);
    }

}
