package com.bignerdranch.android.criminalintent;

/**
 * Created by cmccarty on 11/17/15.
 */
public class CrimeListActivity extends SingleFragmentActivity {


    @Override
    protected CrimeListFragment createFragment() {
        return new CrimeListFragment();
    }
}
