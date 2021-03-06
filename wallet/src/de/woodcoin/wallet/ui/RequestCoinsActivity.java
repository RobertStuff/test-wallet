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

import de.woodcoin.wallet.R;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.lifecycle.ViewModelProvider;

/**
 * @author Andreas Schildbach
 */
public final class RequestCoinsActivity extends AbstractWalletActivity {
    private RequestCoinsActivityViewModel viewModel;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_coins_content);

        viewModel = new ViewModelProvider(this).get(RequestCoinsActivityViewModel.class);
        viewModel.showHelpDialog.observe(this, new Event.Observer<Integer>() {
            @Override
            public void onEvent(final Integer messageResId) {
                HelpDialogFragment.page(getSupportFragmentManager(), messageResId);
            }
        });
    }

    @Override
    public void onAttachedToWindow() {
        setShowWhenLocked(true);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.request_coins_activity_options, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
        case R.id.request_coins_options_help:
            viewModel.showHelpDialog.setValue(new Event<>(R.string.help_request_coins));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
