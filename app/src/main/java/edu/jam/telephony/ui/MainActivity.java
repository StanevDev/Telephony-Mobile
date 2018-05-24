package edu.jam.telephony.ui;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.jam.telephony.R;
import edu.jam.telephony.ui.framgent.AccountFragment;
import edu.jam.telephony.ui.framgent.ChangePlanFragment;
import edu.jam.telephony.ui.framgent.ManageServicesFragment;
import edu.jam.telephony.ui.framgent.PlanAndServicesFragment;
import edu.jam.telephony.ui.framgent.StatisticFragment;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.navigation) BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                .addToBackStack(tag)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.container, new ChangePlanFragment(), tag)
                .commit();
    }

    public void addManagePlanFragment(){
        String tag = "ManagePlan";
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(tag)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.container, new ManageServicesFragment(), tag)
                .commit();
    }

    public void detachChildFragment(){
        getFragmentManager().popBackStack();
        showPlanServiceFragment();
    }

}
