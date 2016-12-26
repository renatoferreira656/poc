package br.com.nextel.cleanversion.bill.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.dextra.cleanversion.R;
import br.com.nextel.cleanversion.bill.chart.ChartPoint;

public class BillPagerFragment extends Fragment {

    private ChartPoint chartPoint;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_collection_object, container, false);
        if (this.chartPoint == null) {
            return rootView;
        }
        TextView currencySymbol = (TextView) rootView.findViewById(R.id.currency_symbol);
        currencySymbol.setShadowLayer(2.0f, 10.0f, 10.0f, Color.argb(10, 0, 0, 0));
        TextView price = (TextView) rootView.findViewById(R.id.price_value);
        price.setText(formatPriceToText(this.chartPoint.getOriginalValue()));
        price.setShadowLayer(2.0f, 10.0f, 10.0f, Color.argb(10, 0, 0, 0));
        TextView cents = (TextView) rootView.findViewById(R.id.price_cents_value);
        cents.setText(formatCentsToText(this.chartPoint.getOriginalValue()));
        cents.setShadowLayer(2.0f, 10.0f, 10.0f, Color.argb(10, 0, 0, 0));

        return rootView;
    }

    private String formatCentsToText(Float originalValue) {
        String text = originalValue.toString();
        String cents = text.split("\\.")[1];
        if (cents.length() < 2) {
            return cents + "0";
        }
        return cents;
    }

    private String formatPriceToText(Float originalValue) {
        String text = originalValue.toString();
        return text.split("\\.")[0] + ",";
    }

    public Fragment init(ChartPoint chartPoint) {
        this.chartPoint = chartPoint;
        return this;
    }
}