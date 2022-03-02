package ru.dopegeek.currex;

import static ru.dopegeek.currex.CurrencyFactory.sCurrencyList;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.dopegeek.currex.response.OptionalResponse;
import ru.dopegeek.currex.response.ResponseModelCurrency;

public class CurrExchFragment extends Fragment {

    private static final String TAG = "CurrExchFragment";
    private RecyclerView mCurrExchRecyclerView;
    private CurrencyAdapter mCurrencyAdapter;
    private MenuItem mRefresh;
    private TextView mDateTextView;

    public static Fragment newInstance() {
        return new CurrExchFragment();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("key", "value");
    }

    public static boolean hasConnection(final Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        wifiInfo = cm.getActiveNetworkInfo();
        return wifiInfo != null && wifiInfo.isConnected();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            String string = savedInstanceState.getString("key");
        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.refresh_list, menu);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_fragment, container, false);
        mCurrExchRecyclerView = (RecyclerView) v.findViewById(R.id.currency_recycler_view);
        mCurrExchRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return v;
    }


    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        updateUI();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        updateUI();
        return super.onOptionsItemSelected(item);
    }

    private void updateUI() {
        if (hasConnection(getActivity())) {
            new DownloadingTask().execute();
        } else {
            Toast.makeText(getActivity(), R.string.no_internet, Toast.LENGTH_LONG).show();
        }
        CurrencyFactory curFab = CurrencyFactory.get(getActivity());
        List<Currency> currencyList = curFab.getCurrencyList();

        if (mCurrencyAdapter == null) {
            mCurrencyAdapter = new CurrencyAdapter(currencyList);
            mCurrExchRecyclerView.setAdapter(mCurrencyAdapter);
        } else {
            mCurrencyAdapter.setValuteList(currencyList);
            mCurrencyAdapter.notifyDataSetChanged();
        }
    }

    private class CurrencyHolder extends RecyclerView.ViewHolder {
        private final TextView mCharCode;
        private final TextView mNominal;
        private final TextView mName;
        private final TextView mValue;
        private Currency mCurrency;


        public CurrencyHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_currency, parent, false));
            mCharCode = itemView.findViewById(R.id.charcode);
            mNominal = itemView.findViewById(R.id.nominal);
            mName = itemView.findViewById(R.id.name);
            mName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopupWindow(v, mCurrency);
                }

            });
            mValue = itemView.findViewById(R.id.value);
        }

        public void bind(Currency currency) {
            mCurrency = currency;
            mCharCode.setText(currency.getCharCode());
            mNominal.setText(currency.getNominal() + "");
            mName.setText(currency.getName());
            mValue.setText(currency.getValue() + "");
        }
    }

    public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyHolder> {
        private List<Currency> mCurrencyList;

        public CurrencyAdapter(List<Currency> currencyList) {
            mCurrencyList = currencyList;
        }

        @NonNull
        @Override
        public CurrencyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new CurrencyHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull CurrencyHolder holder, int position) {
            Currency currency = mCurrencyList.get(position);
            holder.bind(currency);
        }

        public void setValuteList(List<Currency> currencyList) {
            mCurrencyList = currencyList;
        }

        @Override
        public int getItemCount() {
            return mCurrencyList.size();
        }
    }

    private class DownloadingTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            OptionalResponse resp = CurrencyDownloader.getCurrency();
            ResponseModelCurrency[] currenciesModels = resp.get().getValute().getCurrenciesModels();
            sCurrencyList = new ArrayList<>();
            for (int i = 0; i < currenciesModels.length; i++) {
                if (currenciesModels[i] != null) {
                    sCurrencyList.add(new Currency(currenciesModels[i]));
                }
            }
            CurrencyFactory.get(getActivity());
            CurrencyFactory.addCurrency();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
        }
    }

    private void showPopupWindow(View v, Currency cur) {

        View customView = LayoutInflater.from(getContext()).inflate(R.layout.popup_window, null);
        EditText input = customView.findViewById(R.id.input_popup);
        TextView output = customView.findViewById(R.id.output_popup);
        output.setText(String.format("%s", "0"));

        final TextWatcher calculateWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() != 0)
                    output.setText(String.format("%s", cur.getNominal() / cur.getValue() * Float.parseFloat(input.getText().toString())));
            }
        };
        input.addTextChangedListener(calculateWatcher);


        TextView charcodePopup = customView.findViewById(R.id.charcode_popup);
        charcodePopup.setText(cur.getCharCode());
        PopupWindow popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.update();
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
    }
}
