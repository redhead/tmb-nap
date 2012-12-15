package cz.cvut.localtrade;

import cz.cvut.localtrade.helper.Filter;
import cz.cvut.localtrade.helper.RangeSeekBar;
import cz.cvut.localtrade.helper.RangeSeekBar.OnRangeSeekBarChangeListener;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.CompoundButton;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class FilterDialogFragment extends DialogFragment implements
		OnRangeSeekBarChangeListener<Number>, OnSeekBarChangeListener {

	private Filter oldFilter;
	private Filter newFilter;
	private RangeSeekBar<Number> doubleSeekBar;
	private ViewGroup view;
	private SeekBar distanceBar;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		LayoutInflater inflater = getActivity().getLayoutInflater();

		view = (ViewGroup) inflater.inflate(R.layout.filter_items_dialog, null);
		setup();

		doubleSeekBar.setOnRangeSeekBarChangeListener(this);
		if (oldFilter.minPrice != -1 && oldFilter.maxPrice != -1) {
			doubleSeekBar.setSelectedMinValue(oldFilter.minPrice);
			doubleSeekBar.setSelectedMaxValue(oldFilter.maxPrice);
			onRangeSeekBarValuesChanged(doubleSeekBar, oldFilter.minPrice,
					oldFilter.maxPrice);
		} else {
			onRangeSeekBarValuesChanged(doubleSeekBar, oldFilter.priceLowBound,
					oldFilter.priceHighBound);
		}

		if (oldFilter.maxDistance != -1) {
			int val = (int) (oldFilter.maxDistance - oldFilter.distanceLowBound);
			distanceBar.setProgress(val);
			onProgressChanged(distanceBar, val, false);
		} else {
			int val = (int) (oldFilter.distanceHighBound - oldFilter.distanceLowBound);
			distanceBar.setProgress(val);
			onProgressChanged(distanceBar, val, false);
		}

		ScrollView v = new ScrollView(getActivity());
		v.setPadding(15, 15, 15, 15);
		v.addView(view);

		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(v);
		builder.setNegativeButton(R.string.cancel,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				});
		builder.setPositiveButton(R.string.doneButton,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						((SearchedItemsActivity) getActivity())
								.onFilterOk(newFilter);
					}
				});
		return builder.create();
	}

	private void setup() {
		replaceSeekBar();

		distanceBar = (SeekBar) view.findViewById(R.id.seekBar1);
		distanceBar.setOnSeekBarChangeListener(this);
		distanceBar.setMax((int) Math.ceil(oldFilter.distanceHighBound
				- oldFilter.distanceLowBound));

		TextView minPriceText = (TextView) view.findViewById(R.id.minPrice);
		TextView maxPriceText = (TextView) view.findViewById(R.id.maxPrice);

		minPriceText.setText(String.format(getString(R.string.price_value),
				(int) Math.floor(oldFilter.priceLowBound)));
		maxPriceText.setText(String.format(getString(R.string.price_value),
				(int) Math.floor(oldFilter.priceHighBound)));

		CheckBox newCheck = (CheckBox) view.findViewById(R.id.state_new);
		CheckBox usedCheck = (CheckBox) view.findViewById(R.id.state_used);
		CheckBox dysfuncCheck = (CheckBox) view
				.findViewById(R.id.state_dysfunctional);
		CheckBox brokenCheck = (CheckBox) view.findViewById(R.id.state_broken);

		newCheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				newFilter.stateNew = isChecked;
			}
		});
		usedCheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				newFilter.stateUsed = isChecked;
			}
		});
		dysfuncCheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				newFilter.stateDysfunctional = isChecked;
			}
		});
		brokenCheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				newFilter.stateBroken = isChecked;
			}
		});

		newCheck.setChecked(oldFilter.stateNew);
		usedCheck.setChecked(oldFilter.stateUsed);
		dysfuncCheck.setChecked(oldFilter.stateDysfunctional);
		brokenCheck.setChecked(oldFilter.stateBroken);
	}

	private void replaceSeekBar() {
		View seekbar = view.findViewById(R.id.seekBar2);
		LayoutParams layoutParams = (LayoutParams) seekbar.getLayoutParams();
		view.removeView(seekbar);

		doubleSeekBar = new RangeSeekBar<Number>(oldFilter.priceLowBound,
				oldFilter.priceHighBound, getActivity());
		doubleSeekBar.setId(R.id.seekBar2);
		doubleSeekBar.setNotifyWhileDragging(true);

		view.addView(doubleSeekBar, layoutParams);
	}

	public void setFilter(Filter filter) {
		try {
			this.oldFilter = filter;
			this.newFilter = filter.clone();
		} catch (CloneNotSupportedException e) {

		}
	}

	@Override
	public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar,
			Number minValue, Number maxValue) {
		TextView priceValueText = (TextView) view
				.findViewById(R.id.price_range);
		newFilter.minPrice = (Double) minValue;
		newFilter.maxPrice = (Double) maxValue;
		priceValueText.setText(String.format(getString(R.string.price_range),
				minValue.intValue(), maxValue.intValue()));
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		TextView distanceText = (TextView) view
				.findViewById(R.id.distance_value);
		int val = (int) (oldFilter.distanceLowBound + progress);
		distanceText.setText(String.format(getString(R.string.distance_value),
				val));
		newFilter.maxDistance = val;
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
	}
}
