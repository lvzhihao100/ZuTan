package com.eqdd.common.base.loading.waitdialog;


import android.content.Context;


import com.eqdd.common.R;
import com.eqdd.common.base.loading.INetLoadingView;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


public class DialogHelper implements INetLoadingView {


	private WaitDialog dialog;

	public DialogHelper(Context context) {
		dialog = null;
		try {
			dialog = new WaitDialog(context, R.style.common_CDialog);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public void showLoading() {

		dialog.show();
	}

	@Override
	public void showLoading(final String msg) {
		Observable.just(1)
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Action1<Integer>() {
					@Override
					public void call(Integer integer) {
						dialog.setMessage(msg);
						dialog.show();
					}
				});

	}

	@Override
	public void hideLoading(String msg) {
		dialog.dismiss();
	}

	@Override
	public void hideLoading() {
		dialog.dismiss();
	}

}
