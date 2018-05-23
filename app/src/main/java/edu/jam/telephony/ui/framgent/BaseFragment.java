package edu.jam.telephony.ui.framgent;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.widget.Toast;

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

    public BaseFragment() {
        this.disposables = new CompositeDisposable();
    }

    @Override
    public void onDestroyView() {
        if (disposables != null && !disposables.isDisposed())
            disposables.dispose();

        super.onDestroyView();
    }

    protected void disposable(Disposable disposable){
        disposables.add(disposable);
    }

}
