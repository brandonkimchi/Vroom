package com.example.vroomandroidapplicationv4.ui.search.relatedtocalenderview;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.style.ForegroundColorSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Set;

public class DisabledDatesDecorator implements DayViewDecorator {

    private final Set<CalendarDay> disabledDates;
    private final Drawable redBackground; // Background color for disabled dates

    public DisabledDatesDecorator(Set<CalendarDay> dates) {
        this.disabledDates = dates;
        this.redBackground = new ColorDrawable(Color.RED); // Red background
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return disabledDates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setDaysDisabled(true); // Prevent selection
        view.addSpan(new ForegroundColorSpan(Color.WHITE)); // White text color for contrast
        view.setBackgroundDrawable(redBackground); // Set red background for disabled dates
    }
}
