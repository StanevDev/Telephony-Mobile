package edu.jam.telephony.ui.framgent;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import edu.jam.telephony.ui.MainActivity;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class BaseFragment extends Fragment {

    private CompositeDisposable disposables;
    protected Consumer<Throwable> defaultOnError = throwable -> {
        Activity activity = getActivity();

        if (activity != null)
            Toast.makeText(activity, "Network error", Toast.LENGTH_SHORT).show();

        throwable.printStackTrace();
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        this.disposables = new CompositeDisposable();
        super.onCreate(savedInstanceState);
    }

    public BaseFragment() {

    }

    protected void detach(Fragment fragment){
        ((MainActivity)getActivity()).detachChildFragment(fragment);
    }

    protected void detachCalled(){
        ((MainActivity)getActivity()).childDetachCalled();
    }

    protected void disposable(Disposable disposable){
        disposables.add(disposable);
    }

}
