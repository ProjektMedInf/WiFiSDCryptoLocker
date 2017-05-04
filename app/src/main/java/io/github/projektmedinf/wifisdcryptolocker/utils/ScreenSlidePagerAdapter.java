package io.github.projektmedinf.wifisdcryptolocker.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import io.github.projektmedinf.wifisdcryptolocker.R;

/**
 * This class was originally written by matrixxun
 * https://github.com/matrixxun/ProductTour
 */
public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

    private int numberOfPages;

    public ScreenSlidePagerAdapter(FragmentManager fm, int numberOfPages) {
        super(fm);
        this.numberOfPages = numberOfPages;
    }

    @Override
    public Fragment getItem(int position) {
        SetupFragment tp = null;
        switch (position) {
            case 0:
                tp = SetupFragment.newInstance(R.layout.setup_page_1);
                break;
            case 1:
                tp = SetupFragment.newInstance(R.layout.setup_page_2);
                break;
            case 2:
                tp = SetupFragment.newInstance(R.layout.setup_page_3);
                break;
            case 3:
                tp = SetupFragment.newInstance(R.layout.setup_page_4);
                break;
            default:
                tp = SetupFragment.newInstance(R.layout.chinese_magic);
                break;
        }
        return tp;
    }

    @Override
    public int getCount() {
        return numberOfPages;
    }
}