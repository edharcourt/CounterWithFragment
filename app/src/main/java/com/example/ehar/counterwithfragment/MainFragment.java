package com.example.ehar.counterwithfragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;


public class MainFragment extends Fragment {

    Button start = null;
    Button reset = null;
    TextView count = null;
    Counter c = null;
    Timer t = null;
    boolean running = false;
    Handler h = null;

    public MainFragment() { }  // required?

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MainFragment.
     */
    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    /**
     * Not called on orientation change
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        this.c = new Counter(0);
        this.t = new Timer();
        this.h = new Handler();
    }

    @Override
    public View onCreateView(
        LayoutInflater inflater,
        ViewGroup container,    // parent
        Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        this.start = (Button) root.findViewById(R.id.start);
        this.reset = (Button) root.findViewById(R.id.reset);
        this.count = (TextView) root.findViewById(R.id.count);

        start.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!MainFragment.this.running) {
                            start.setEnabled(false);
                            t.scheduleAtFixedRate(c, 0, 1000);
                            MainFragment.this.running = true;
                        }
                    }
                }
        );


        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        this.t.cancel();
        this.h.removeCallbacksAndMessages(null);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /* -------------------------- */
    class Counter extends TimerTask {

        long count = 0;

        public Counter(long i ) { count = i; }

        @Override
        public void run() {
            /* getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    MainFragment.this.count.setText(Long.toString(count));
                    count++;
                }
            }); */


            // runnnable is run on thread to which this
            // handler is attached.
            // can't use getActivity() because the activity gets
            // killed but the fragment doesn't
            h.post(new Runnable() {
                public void run() {
                    MainFragment.this.count.setText(Long.toString(count));
                    count++;
                    if (count % 5 == 0) {
                        Toast.makeText(getContext(), "Running: " + count, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}
