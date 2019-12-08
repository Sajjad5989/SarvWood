package ir.sarvwood.workshop.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mlsdev.animatedrv.AnimatedRecyclerView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import ir.sarvwood.workshop.R;

public class SelectByUserFragment extends Fragment {


    private static String[]  value;
    @BindView(R.id.recycler_selected_values)
    protected AnimatedRecyclerView selectionValueRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_by_user, container, false);

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);
    }

    public static SelectByUserFragment newInstance(String[]  inputValue) {
        value = inputValue;
        return new SelectByUserFragment();
    }



}
