package edu.jam.telephony.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.jam.telephony.R;
import edu.jam.telephony.model.AccountSaver;
import edu.jam.telephony.ui.framgent.AccountFragment;
import edu.jam.telephony.ui.framgent.ChangePlanFragment;
import edu.jam.telephony.ui.framgent.ManageServicesFragment;
import edu.jam.telephony.ui.framgent.PlanAndServicesFragment;
import edu.jam.telephony.ui.framgent.StatisticFragment;
import edu.jam.telephony.ui.framgent.TechRequestsFragment;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.navigation) BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!(new AccountSaver(this).hasSub())){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        showAccountFragment();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        showAccountFragment();
                        return true;
                    case R.id.navigation_dashboard:
                        showPlanServiceFragment();
                        return true;
                    case R.id.navigation_notifications:
                        showStatisticsFragment();
                        return true;
                }
                return false;
            };

    private void showAccountFragment(){
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.container, new AccountFragment(), "Account")
                .commit();
    }

    private void showPlanServiceFragment(){
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.container, new PlanAndServicesFragment(), "PlanService")
                .commit();
    }

    private void showStatisticsFragment(){
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.container, new StatisticFragment(), "Statistic")
                .commit();
    }

    public void addChangePlanFragmentFragment(){
        String tag = "ChangePlan";
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.container, new ChangePlanFragment(), tag)
                .commit();
    }

    public void addManagePlanFragment(){
        String tag = "ManagePlan";
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.container, new ManageServicesFragment(), tag)
                .commit();
    }

    public void addTechRequestFragment(){
        String tag = "techRequest";

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.container, new TechRequestsFragment())
                .commit();
    }

    public void detachChildFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().detach(fragment).disallowAddToBackStack().remove(fragment).commitAllowingStateLoss();

        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        System.out.println();
        childDetachCalled();
    }

    public void childDetachCalled(){
        List<Fragment> fragments = getSupportFragmentManager().getFragments();

        if (fragments != null && !fragments.isEmpty())
            for (Fragment frag : fragments) {
                System.out.println(frag.getTag());
                frag.onResume();
            }
    };

}
