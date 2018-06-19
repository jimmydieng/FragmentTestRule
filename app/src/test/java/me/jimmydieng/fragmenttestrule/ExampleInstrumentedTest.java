package me.jimmydieng.fragmenttestrule;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.when;

/**
 * Instrumented test, which will execute on an Android device.
 */
@RunWith(MockitoJUnitRunner.class)
public class ExampleInstrumentedTest {

    @IdRes private static final int TEXT_FIELD = R.id.text_view;
    private static final String ALTERNATIVE_MESSAGE = "HELLO WORLD";

    @Mock private MessageProvider messageProvider;

    @Rule
    public FragmentTestRule<BlankFragment> testFragmentRule =
            new FragmentTestRule<BlankFragment>(BlankFragment.class) {
                @NonNull
                @Override
                protected BlankFragment getFragmentInstance() {
                    return new BlankFragment();
                }

                @Override
                protected void afterFragmentAttached(@NonNull Fragment fragment) {
                    if (!BlankFragment.class.isInstance(fragment)) {
                        return;
                    }
                    BlankFragment blankFragment = (BlankFragment) fragment;
                    blankFragment.messageProvider = messageProvider;
                }
            };

    @Before
    public void setUp() {
        when(messageProvider.getMessage()).thenReturn(ALTERNATIVE_MESSAGE);
    }

    @Test
    public void testItWorks() {
        onView(withId(TEXT_FIELD)).check(matches(isDisplayed()));
        onView(withId(TEXT_FIELD)).check(matches(withText(ALTERNATIVE_MESSAGE)));
    }
}
