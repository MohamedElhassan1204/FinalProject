package algonquin.cst2335.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.algonquin.cst2335.geld0011.R;

public class CurrencyFragment extends Fragment {

    private boolean isTablet;
    private Bundle dataFromActivity;
    private long id;
    private int position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dataFromActivity = getArguments();
        id = dataFromActivity.getLong(CurrencyConverter.ITEM_ID);
        position = dataFromActivity.getInt(CurrencyConverter.ITEM_POSITION);

        String baseCurrency = dataFromActivity.getString(CurrencyConverter.BASE_CURRENCY);
        String targetCurrency = dataFromActivity.getString(CurrencyConverter.TARGET_CURRENCY);

        View result = inflater.inflate(R.layout.fragment_currency, container, false);

        TextView currencyNameTextView = result.findViewById(R.id.currency_name);
        TextView targetCurrencyTextView = result.findViewById(R.id.target_currency);
        TextView idTextView = result.findViewById(R.id.id_number);

        Button deleteButton = result.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isTablet) {
                    CurrencyConverter parent = (CurrencyConverter) getActivity();
                    parent.deleteMessageId((int) id, position);
                    parent.getSupportFragmentManager().beginTransaction().remove(CurrencyFragment.this).commit();
                } else {
                    CurrencyEmptyActivity parent = (CurrencyEmptyActivity) getActivity();
                    Intent backToFragmentExample = new Intent();
                    backToFragmentExample.putExtra(CurrencyConverter.ITEM_ID, id);
                    backToFragmentExample.putExtra(CurrencyConverter.ITEM_POSITION, position);
                    backToFragmentExample.putExtra(CurrencyConverter.BASE_CURRENCY, baseCurrency);
                    backToFragmentExample.putExtra(CurrencyConverter.TARGET_CURRENCY, targetCurrency);
                    parent.setResult(Activity.RESULT_OK, backToFragmentExample);
                    parent.finish();
                }
            }
        });

        return result;
    }

    public void setTablet(boolean b) {
    }
}
