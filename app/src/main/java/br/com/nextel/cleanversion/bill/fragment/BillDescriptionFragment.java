package br.com.nextel.cleanversion.bill.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.dextra.cleanversion.R;
import br.com.nextel.cleanversion.bill.util.PriceUtils;
import br.com.nextel.cleanversion.bill.chart.ChartPoint;

public class BillDescriptionFragment extends Fragment {

    private ChartPoint point;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bill_description, container, false);
        if(point == null){
            return rootView;
        }
        double text = point.getOriginalValue() * (3.0 / 6.0);
        setText(rootView.findViewById(R.id.actual_plan), PriceUtils.formatValueToText(text));
        text = point.getOriginalValue() * (1.0 / 6.0);
        setText(rootView.findViewById(R.id.beyond_planned), PriceUtils.formatValueToText(text));
        text = point.getOriginalValue() * (2.0 / 6.0);
        setText(rootView.findViewById(R.id.other_bills), PriceUtils.formatValueToText(text));
        return rootView;
    }

    private void setText(View view, String text) {
        TextView textView = (TextView) view;
        textView.setText(text);
    }

    public BillDescriptionFragment init(ChartPoint point) {
        this.point = point;
        return this;
    }
}
