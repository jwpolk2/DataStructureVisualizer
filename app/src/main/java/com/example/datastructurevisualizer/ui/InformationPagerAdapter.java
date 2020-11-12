package com.example.datastructurevisualizer.ui;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class InformationPagerAdapter extends FragmentStateAdapter {
    private String dataStructureType;

    public InformationPagerAdapter(Fragment fragment, String dataStructureType) {
        super(fragment);
        this.dataStructureType = dataStructureType;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new Overview(dataStructureType);
            case 2:
                return new Complexity(dataStructureType);
            default:
                return new Code(dataStructureType);

        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
