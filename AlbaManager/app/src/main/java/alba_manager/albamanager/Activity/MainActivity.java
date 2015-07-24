package alba_manager.albamanager.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import alba_manager.albamanager.Fragment.NavigationDrawerFragment;
import alba_manager.albamanager.Fragment.PayFragment;
import alba_manager.albamanager.Fragment.RegiAlbarFragment;
import alba_manager.albamanager.Fragment.ScheduleFragment;
import alba_manager.albamanager.R;
import alba_manager.albamanager.Util.BackPressCloseHandler;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private NavigationDrawerFragment mNavigationDrawerFragment;

    private CharSequence mTitle;

    //두번 뒤로가기시 처리용 클레스 선언
    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //두번 뒤로가기 처리용 객체 생성
        backPressCloseHandler = new BackPressCloseHandler(this);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onBackPressed() {
        //두번 뒤로가기 처리
        backPressCloseHandler.onBackPressed();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);

                fragmentManager.beginTransaction()
                        .replace(R.id.container, ScheduleFragment.newInstance())
                        .commit();

                break;
            case 2:
                mTitle = getString(R.string.title_section2);

                fragmentManager.beginTransaction()
                        .replace(R.id.container, PayFragment.newInstance())
                        .commit();

                break;
            case 3:
                mTitle = getString(R.string.title_section3);

                fragmentManager.beginTransaction()
                        .replace(R.id.container, RegiAlbarFragment.newInstance())
                        .commit();
                break;
        }

        restoreActionBar();
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
