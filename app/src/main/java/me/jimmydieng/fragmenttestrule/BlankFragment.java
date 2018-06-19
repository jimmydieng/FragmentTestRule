package me.jimmydieng.fragmenttestrule;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {

    MessageProvider messageProvider;
    private TextView textView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        messageProvider = new MessageProvider();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank, container, false);

        textView = view.findViewById(R.id.text_view);
        textView.setText(messageProvider.getMessage());

        return view;
    }
}
