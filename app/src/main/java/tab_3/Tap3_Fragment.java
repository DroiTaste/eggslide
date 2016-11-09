package tab_3;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;
import ultra.seed.eggslide.R;

public class Tap3_Fragment extends Fragment implements MaterialTabListener {

    //머터리얼 탭 호스트
    private MaterialTabHost tabHost;

    //뷰페이저
    private ViewPager mViewPager;
    private pagerAdapter adapter;
    private static final int NUM_PAGES = 2;//페이지 수
    private int temp = 0; //현재 페이지
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab3_fragment, container, false);

        //탭 호스트 갯수
        tabHost = (MaterialTabHost) v.findViewById(R.id.materialTabHost);
        for (int i = 0; i < 2; i++) {
            tabHost.addTab(
                    tabHost.newTab()
                            //.setIcon(getIcon(i))
                            .setText(getTitle(i))
                            .setTabListener(this)
            );
        }
        //최초 선택
        tabHost.setSelectedNavigationItem(0);

        //뷰페이저
        mViewPager = (ViewPager) v.findViewById(R.id.pager);
        adapter = new pagerAdapter(getFragmentManager());
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(temp);            // 현재 페이지
        mViewPager.setOffscreenPageLimit(2);        // 미리 불러오는 화면 갯수
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                temp = position;
                tabHost.setSelectedNavigationItem(temp);

                switch (temp) {
                    case 0:

                        break;
                    case 1:

                        break;
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub
            }
        });
        return v;
    }
    private class pagerAdapter extends FragmentPagerAdapter {

        public pagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
            if (object instanceof Fragment) {
                Fragment fragment = (Fragment) object;
                android.support.v4.app.FragmentManager fm = fragment.getFragmentManager();
                android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
                ft.remove(fragment);
                this.notifyDataSetChanged();
                ft.commitAllowingStateLoss();
            }
        }

        @Override
        public Fragment getItem(int position) {

            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new Tap3_Following_Fragment();
                    break;
                case 1:
                    fragment = new Tap3_Wish_Fragment();
                    break;
                default:
                    return null;
            }

            return fragment;
        }


        // 생성 가능한 페이지 개수를 반환해준다.
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return NUM_PAGES;
        }


    }
    //탭 호스트 타이틀
    private String getTitle(int position){
        String title = "";
        switch (position){
            case 0:
                title = "팔로잉";
                break;
            case 1:
                title = "위시 리스트";
                break;
        }
        return title;
    }
    // 탭 호스트 탭했을때의 동작
    @Override
    public void onTabSelected(MaterialTab tab) {
        // when the tab is clicked the pager swipe content to the tab position
        //mViewPager.setCurrentItem(tab.getPosition());
        switch (tab.getPosition()){
            case 0:
                mViewPager.setCurrentItem(tab.getPosition());
                tabHost.setSelectedNavigationItem(tab.getPosition());
                break;
            case 1:
                mViewPager.setCurrentItem(tab.getPosition());
                tabHost.setSelectedNavigationItem(tab.getPosition());
                break;
        }
    }
    @Override
    public void onTabUnselected(MaterialTab tab) {

    }
    @Override
    public void onTabReselected(MaterialTab tab) {

    }

}
