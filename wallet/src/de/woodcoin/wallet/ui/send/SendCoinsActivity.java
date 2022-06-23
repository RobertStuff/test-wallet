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

package de.woodcoin.wallet.ui.send;

import javax.annotation.Nullable;

import org.bitcoinj.core.Coin;

import de.woodcoin.wallet.Constants;
import de.woodcoin.wallet.R;
import de.woodcoin.wallet.data.PaymentIntent;
import de.woodcoin.wallet.service.BlockchainService;
import de.woodcoin.wallet.ui.AbstractWalletActivity;
import de.woodcoin.wallet.ui.Event;
import de.woodcoin.wallet.ui.HelpDialogFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.lifecycle.ViewModelProvider;

/**
 * @author Andreas Schildbach
 */
public final class SendCoinsActivity extends AbstractWalletActivity {
    public static final String INTENT_EXTRA_PAYMENT_INTENT = "payment_intent";
    public static final String INTENT_EXTRA_FEE_CATEGORY = "fee_category";

    public static void start(final Context context, final PaymentIntent paymentIntent,
            final @Nullable FeeCategory feeCategory, final int intentFlags) {
        final Intent intent = new Intent(context, SendCoinsActivity.class);
        intent.putExtra(INTENT_EXTRA_PAYMENT_INTENT, paymentIntent);
        if (feeCategory != null)
            intent.putExtra(INTENT_EXTRA_FEE_CATEGORY, feeCategory);
        if (intentFlags != 0)
            intent.setFlags(intentFlags);
        context.startActivity(intent);
    }

    public static void start(final Context context, final PaymentIntent paymentIntent) {
        start(context, paymentIntent, null, 0);
    }

    public static void startDonate(final Context context, final Coin amount, final @Nullable FeeCategory feeCategory,
            final int intentFlags) {
        start(context, PaymentIntent.from(Constants.DONATION_ADDRESS,
                context.getString(R.string.wallet_donate_address_label), amount), feeCategory, intentFlags);
    }

    private SendCoinsActivityViewModel viewModel;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_coins_content);

        viewModel = new ViewModelProvider(this).get(SendCoinsActivityViewModel.class);
        viewModel.showHelpDialog.observe(this, new Event.Observer<Integer>() {
            @Override
            public void onEvent(final Integer messageResId) {
                HelpDialogFragment.page(getSupportFragmentManager(), messageResId);
            }
        });

        BlockchainService.start(this, false);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.send_coins_activity_options, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
        case R.id.send_coins_options_help:
            viewModel.showHelpDialog.setValue(new Event<>(R.string.help_send_coins));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
