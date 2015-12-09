package com.applepluot.todoapp;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class UiAutomatorTest {

    private static final String BASIC_SAMPLE_PACKAGE
            = "com.applepluot.todoapp";
    private static final int LAUNCH_TIMEOUT = 5000;
    private UiDevice mDevice;

    @Before
    public void startMainActivityFromHomeScreen() {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // Start from the home screen
        mDevice.pressHome();

        // Wait for launcher
        final String launcherPackage = mDevice.getLauncherPackageName();
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)),
                LAUNCH_TIMEOUT);

        // Launch the app
        Context context = InstrumentationRegistry.getContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(BASIC_SAMPLE_PACKAGE);
        // Clear out any previous instances
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        // Wait for the app to appear
        mDevice.wait(Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE).depth(0)),
                LAUNCH_TIMEOUT);
    }
    @Test
    public void testAppLaunch() {
        UiObject2 addButton = mDevice.findObject(By.res(BASIC_SAMPLE_PACKAGE, "btnAdd"));
        assertTrue(addButton.isEnabled());
    }

    @Test
    public void testAddItem() {
        UiObject2 addButton = mDevice.findObject(By.res(BASIC_SAMPLE_PACKAGE, "btnAdd"));
        UiObject2 editText = mDevice.findObject(By.res(BASIC_SAMPLE_PACKAGE, "etAddItemText"));
        editText.setText("New Item");
        addButton.click();
        UiObject2 newItem = mDevice.findObject(By.textContains("New Item"));
        newItem.longClick();
    }
}
