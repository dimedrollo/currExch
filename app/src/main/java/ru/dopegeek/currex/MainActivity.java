package ru.dopegeek.currex;

import androidx.fragment.app.Fragment;

public class MainActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return CurrExchFragment.newInstance();
    }

}