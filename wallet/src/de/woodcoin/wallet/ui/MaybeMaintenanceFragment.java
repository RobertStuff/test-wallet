/*
 * Copyright the original author or authors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package de.woodcoin.wallet.ui;

import de.woodcoin.wallet.ui.send.MaintenanceDialogFragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

/**
 * @author Andreas Schildbach
 */
public class MaybeMaintenanceFragment extends Fragment {
    private static final String FRAGMENT_TAG = MaybeMaintenanceFragment.class.getName();

    public static void add(final FragmentManager fm) {
        Fragment fragment = fm.findFragmentByTag(FRAGMENT_TAG);
        if (fragment == null) {
            fragment = new MaybeMaintenanceFragment();
            fm.beginTransaction().add(fragment, FRAGMENT_TAG).commit();
        }
    }

    private MaybeMaintenanceViewModel viewModel;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MaybeMaintenanceViewModel.class);
        viewModel.showDialog.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(final Void v) {
                if (!viewModel.getDialogWasShown()) {
                    MaintenanceDialogFragment.show(getFragmentManager());
                    viewModel.setDialogWasShown();
                }
            }
        });
    }
}
