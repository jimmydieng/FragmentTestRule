package me.jimmydieng.fragmenttestrule;

import android.app.Instrumentation;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;


/**
 * This rule provides functional testing of a single fragment.
 * <p>
 * Idea extracted from: https://github.com/21Buttons/FragmentTestRule
 *
 * @param <T> The fragment under test
 */
public class FragmentTestRule<T extends Fragment> extends ActivityTestRule<MainActivity> {

    @NonNull private final Class<T> fragmentClass;
    @NonNull private Instrumentation instrumentation;

    private FragmentManager.FragmentLifecycleCallbacks fragmentLifecycleCallbacks =
            new FragmentManager.FragmentLifecycleCallbacks() {
                @Override
                public void onFragmentAttached(FragmentManager fm, Fragment f, Context context) {
                    afterFragmentAttached(f);
                }
            };

    private T fragment;

    /**
     * Creates a fragment test rule for the fragment under test.
     *
     * @param clazz The fragment class under test.
     */
    public FragmentTestRule(@NonNull Class<T> clazz) {
        super(MainActivity.class);
        this.fragmentClass = clazz;
        this.instrumentation = InstrumentationRegistry.getInstrumentation();
    }

    /**
     * Override this method to set up the desired Fragment.
     * <p>
     * The default Fragment (if this method returns null or is not overwritten) is:
     * fragmentClass.newInstance()
     */
    @NonNull
    protected T getFragmentInstance() {
        try {
            return fragmentClass.newInstance();
        } catch (ReflectiveOperationException e) {
            throw new AssertionError("Failed to instantiate desired fragment class "
                    + fragmentClass + ". Are you providing the required Android no-param constructor?");
        }
    }

    @Override
    protected void afterActivityLaunched() {
        super.afterActivityLaunched();

        final MainActivity activity = getActivity();
        instrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                FragmentManager manager = activity.getSupportFragmentManager();
                manager.registerFragmentLifecycleCallbacks(fragmentLifecycleCallbacks, false);

                FragmentTestRule.this.fragment = getFragmentInstance();

                manager.beginTransaction()
                        .add(android.R.id.content, fragment)
                        .commit();
            }
        });
    }

    /**
     * Override this method to  for when the fragment has just gotten attached
     */
    protected void afterFragmentAttached(@NonNull Fragment fragment) {
        // Left to be overridden
    }

    /**
     * Returns the fragment under test.
     */
    @NonNull
    public T getFragment() {
        if (fragment == null) {
            throw new IllegalStateException("Attempted to retrieve fragment before creation");
        }
        return fragment;
    }
}
