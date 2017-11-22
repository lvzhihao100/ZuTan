package com.eqdd.common.base.loading.waitdialog;



import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.eqdd.common.R;


public class WaitDialog extends Dialog {
	private TextView tvMessage;

//	private TextView _messageTv;

	public WaitDialog(Context context) {
		super(context);
		init(context);
	}

	public WaitDialog(Context context, int defStyle) {
		super(context, defStyle);
		init(context);
	}

	protected WaitDialog(Context context, boolean cancelable,
						 OnCancelListener listener) {
		super(context, cancelable, listener);
		init(context);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.dismiss();
	}


	private void init(Context context) {
		setCancelable(false);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		View view = LayoutInflater.from(context).inflate(R.layout.common_dialog_loading_layout,
				null);
		tvMessage = (TextView) view.findViewById(R.id.tv_progress);
		setContentView(view);
	}


	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		if (TDevice.isTablet()) {
			int i = (int) TDevice.dpToPixel(360F);
			if (i < TDevice.getScreenWidth()) {
				WindowManager.LayoutParams params = getWindow().getAttributes();
				params.width = i;
				getWindow().setAttributes(params);
			}
		}
	}

	public void setMessage(String message) {
		tvMessage.setText(message);
	}
}
