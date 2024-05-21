package androidsamples.java.journalapp;

import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.Objects;
import androidx.appcompat.view.menu.ActionMenuItem;
import androidx.fragment.app.FragmentFactory;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.navigation.Navigation;
import androidx.navigation.testing.TestNavHostController;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * Instrumented tests for the {@link EntryListFragment}.
 */
@RunWith(AndroidJUnit4.class)
public class EntryListFragmentTest {
    @Test
    public void testNavigationToEntryDetailsFragment() {
        // Create a TestNavHostController
        TestNavHostController navController = new TestNavHostController(
                ApplicationProvider.getApplicationContext());

        FragmentScenario<EntryListFragment> entryListFragmentFragmentScenario
                = FragmentScenario.launchInContainer(EntryListFragment.class, null, R.style.Theme_JournalApp, (FragmentFactory) null);

        entryListFragmentFragmentScenario.onFragment(fragment -> {
            // Set the graph on the TestNavHostController
            navController.setGraph(R.navigation.nav_graph);

            // Make the NavController available via the findNavController() APIs
            Navigation.setViewNavController(fragment.requireView(), navController);
        });

        // Verify that performing a click changes the NavController's state
        onView(withId(R.id.btn_add_entry)).perform(ViewActions.click());
        assertThat(Objects.requireNonNull(navController.getCurrentDestination()).getId(), is(R.id.entryDetailsFragment));

    }

    @Test
    public void testNavigationToInfoFragment() {
        TestNavHostController navController = new TestNavHostController(
                ApplicationProvider.getApplicationContext());

        FragmentScenario<EntryListFragment> entryListFragmentFragmentScenario
                = FragmentScenario.launchInContainer(EntryListFragment.class, null, R.style.Theme_MaterialComponents_DayNight_DarkActionBar, (FragmentFactory) null);

        Context context = ApplicationProvider.getApplicationContext();
        ActionMenuItem infoMenuItem = new ActionMenuItem(context, 0, R.id.info, 0, 0, null);

        entryListFragmentFragmentScenario.onFragment(fragment -> {
            // Set the graph on the TestNavHostController
            navController.setGraph(R.navigation.nav_graph);

            // Make the NavController available via the findNavController() APIs
            Navigation.setViewNavController(fragment.requireView(), navController);

            fragment.onOptionsItemSelected(infoMenuItem);
        });

        assertThat(Objects.requireNonNull(navController.getCurrentDestination()).getId(), is(R.id.infoFragment));
    }

    @Test
    public void clickingOnFirstEntry() throws InterruptedException {
        TestNavHostController navController = new TestNavHostController(
                ApplicationProvider.getApplicationContext());

        FragmentScenario<EntryListFragment> entryListFragmentFragmentScenario
                = FragmentScenario.launchInContainer(EntryListFragment.class, null, R.style.Theme_MaterialComponents_DayNight_DarkActionBar, (FragmentFactory) null);


        entryListFragmentFragmentScenario.onFragment(fragment -> {
            // Set the graph on the TestNavHostController
            navController.setGraph(R.navigation.nav_graph);

            // Make the NavController available via the findNavController() APIs
            Navigation.setViewNavController(fragment.requireView(), navController);


        });

        Thread.sleep(3000); // Introduce a sleep to wait for animations

        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));
        assertThat(Objects.requireNonNull(navController.getCurrentDestination()).getId(), is(R.id.entryDetailsFragment));
    }
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("androidsamples.java.journalapp", appContext.getPackageName());
    }
}