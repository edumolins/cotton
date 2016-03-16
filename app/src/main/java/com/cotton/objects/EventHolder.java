package com.cotton.objects;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class EventHolder {

	private TextView dayEvent;
	private TextView monthEvent;
	private TextView nameEvent;
	private TextView locationEvent;
	private ImageView viewEvent;
	private RelativeLayout descriptionLayoutEvent;
	private TextView descriptionEvent;
	private RelativeLayout bookLayoutEvent;
	private ImageView imageEvent;

	public EventHolder(TextView dayEvent, TextView monthEvent, TextView nameEvent, TextView locationEvent, ImageView viewEvent, RelativeLayout descriptionLayoutEvent, TextView descriptionEvent, RelativeLayout bookLayoutEvent, ImageView imageEvent) {
		this.dayEvent = dayEvent;
		this.monthEvent = monthEvent;
		this.nameEvent = nameEvent;
		this.locationEvent = locationEvent;
		this.viewEvent = viewEvent;
		this.descriptionLayoutEvent = descriptionLayoutEvent;
		this.descriptionEvent = descriptionEvent;
		this.bookLayoutEvent = bookLayoutEvent;
		this.imageEvent = imageEvent;
	}

	public TextView getDayEvent() {
		return dayEvent;
	}

	public void setDayEvent(TextView dayEvent) {
		this.dayEvent = dayEvent;
	}

	public TextView getMonthEvent() {
		return monthEvent;
	}

	public void setMonthEvent(TextView monthEvent) {
		this.monthEvent = monthEvent;
	}

	public TextView getNameEvent() {
		return nameEvent;
	}

	public void setNameEvent(TextView nameEvent) {
		this.nameEvent = nameEvent;
	}

	public TextView getLocationEvent() {
		return locationEvent;
	}

	public void setLocationEvent(TextView locationEvent) {
		this.locationEvent = locationEvent;
	}

	public ImageView getViewEvent() {
		return viewEvent;
	}

	public void setViewEvent(ImageView viewEvent) {
		this.viewEvent = viewEvent;
	}

	public RelativeLayout getDescriptionLayoutEvent() {
		return descriptionLayoutEvent;
	}

	public void setDescriptionLayoutEvent(RelativeLayout descriptionLayoutEvent) {
		this.descriptionLayoutEvent = descriptionLayoutEvent;
	}

	public TextView getDescriptionEvent() {
		return descriptionEvent;
	}

	public void setDescriptionEvent(TextView descriptionEvent) {
		this.descriptionEvent = descriptionEvent;
	}

	public RelativeLayout getBookLayoutEvent() {
		return bookLayoutEvent;
	}

	public void setBookLayoutEvent(RelativeLayout bookLayoutEvent) {
		this.bookLayoutEvent = bookLayoutEvent;
	}

	public ImageView getImageEvent() {
		return imageEvent;
	}

	public void setImageEvent(ImageView imageEvent) {
		this.imageEvent = imageEvent;
	}
}