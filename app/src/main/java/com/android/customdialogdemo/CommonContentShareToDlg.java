package com.android.customdialogdemo;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

public class CommonContentShareToDlg extends Dialog {
	private final static String TAG = "CommonContentShareToDlg";
	public final static int SHARE_TO_ICON_NUM = 5;
	public final static int SHARE_TO_ICON_RENREN = 0;
	public final static int SHARE_TO_ICON_WEIXIN_SOCIAL_CIRCLE = SHARE_TO_ICON_RENREN + 1;
	public final static int SHARE_TO_ICON_WEIXIN_CONTACTS = SHARE_TO_ICON_RENREN + 2;
	public final static int SHARE_TO_ICON_QQ = SHARE_TO_ICON_RENREN + 3;
	public final static int SHARE_TO_ICON_WEIBO = SHARE_TO_ICON_RENREN + 4;

	public final static int DLG_TEXT_ITEM_NUM = 4;
	public final static int DLG_TEXT_ITEM_NUM_CUSTOM = 3; // doesn't count
															// Cancel button
	public final static int DLG_TEXT_ITEM_CUSTOM_1 = SHARE_TO_ICON_WEIBO + 1;
	public final static int DLG_TEXT_ITEM_CUSTOM_2 = SHARE_TO_ICON_WEIBO + 2;
	public final static int DLG_TEXT_ITEM_CUSTOM_3 = SHARE_TO_ICON_WEIBO + 3;
	// NOTE: DLG_TEXT_ITEM_CANCEL click event would NOT be returned to
	// mDialogItemClickListener,
	// because the event would be handled by CancelListener.
	public final static int DLG_TEXT_ITEM_CANCEL = SHARE_TO_ICON_WEIBO + 4;
	private Context ctx;

	protected final static int[] shareToIconViewResIds = new int[] {
			R.id.dialog_share_to_icon_1, R.id.dialog_share_to_icon_2,
			R.id.dialog_share_to_icon_3, R.id.dialog_share_to_icon_4,
			R.id.dialog_share_to_icon_5 };
	protected final static int[] textItemViewResIds = new int[] {
			R.id.dialog_text_item_1, R.id.dialog_text_item_2,
			R.id.dialog_text_item_3, R.id.dialog_text_item_cancel };

	protected final static int[] shareToIconIds = new int[] {
			SHARE_TO_ICON_RENREN, SHARE_TO_ICON_WEIXIN_SOCIAL_CIRCLE,
			SHARE_TO_ICON_WEIXIN_CONTACTS, SHARE_TO_ICON_QQ,
			SHARE_TO_ICON_WEIBO };
	protected final static int[] textItemIds = new int[] {
			DLG_TEXT_ITEM_CUSTOM_1, DLG_TEXT_ITEM_CUSTOM_2,
			DLG_TEXT_ITEM_CUSTOM_3, DLG_TEXT_ITEM_CANCEL };

	protected View mContentView;
	protected LayoutInflater mInflater;
	protected Handler mHandler;

	protected OnDialogItemClickListener mDialogItemClickListener;
	protected ImageView[] shareToImageViews;
	protected TextView[] textItemViews;
	protected String[] mCustomItemTexts;

	public CommonContentShareToDlg(Context context) {
		super(context, R.style.full_width_dialog_at_screen_bottom);
		ctx = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initDialog();
	}

	protected void initDialog() {
		initViews();
		initAttributes();
		bindListeners();
	}

	protected void initAttributes() {
		WindowManager.LayoutParams layoutParams = this.getWindow()
				.getAttributes();
		layoutParams.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
		layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
		layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
		setCanceledOnTouchOutside(true);
	}

	protected void initViews() {
		mInflater = getLayoutInflater();
		mContentView = mInflater.inflate(
				R.layout.common_dialog_content_share_to_layout, null, true);
		setContentView(mContentView);

		shareToImageViews = new ImageView[SHARE_TO_ICON_NUM];
		for (int i = 0; i < SHARE_TO_ICON_NUM; i++) {
			shareToImageViews[i] = (ImageView) mContentView
					.findViewById(shareToIconViewResIds[i]);
		}
		textItemViews = new TextView[DLG_TEXT_ITEM_NUM];
		for (int i = 0; i < DLG_TEXT_ITEM_NUM; i++) {
			textItemViews[i] = (TextView) mContentView
					.findViewById(textItemViewResIds[i]);
		}

		// sets the items customized by dialog user visible with custom texts
		if (mCustomItemTexts != null && textItemViews != null) {
			int numSetItems = mCustomItemTexts.length >= DLG_TEXT_ITEM_NUM_CUSTOM ? DLG_TEXT_ITEM_NUM_CUSTOM
					: mCustomItemTexts.length;
			for (int i = 0; i < numSetItems; i++) {
				textItemViews[i].setText(mCustomItemTexts[i]);
				textItemViews[i].setVisibility(View.VISIBLE);
			}
		}
	}

	protected void bindListeners() {
		if (shareToImageViews != null) {
			for (int i = 0; i < SHARE_TO_ICON_NUM; i++) {
				final int viewIdx = i;
				shareToImageViews[viewIdx]
						.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								dismiss();
								if (mDialogItemClickListener != null) {
									mDialogItemClickListener
											.onDialogItemClick(shareToIconIds[viewIdx]);
								}
							}
						});
			}
		}

		if (textItemViews != null) {
			for (int i = 0; i < DLG_TEXT_ITEM_NUM_CUSTOM; i++) {
				final int viewIdx = i;
				textItemViews[viewIdx]
						.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								dismiss();
								if (mDialogItemClickListener != null) {
									mDialogItemClickListener
											.onDialogItemClick(textItemIds[viewIdx]);
								}
							}
						});
			}
			textItemViews[DLG_TEXT_ITEM_CANCEL - DLG_TEXT_ITEM_CUSTOM_1]
					.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							cancel();
						}
					});
		}
	}

	public void setCustomItems(String[] textItems) {
		mCustomItemTexts = textItems;
	}

	public void setDialogItemClickListener(
			OnDialogItemClickListener dialogItemClickListener) {
		mDialogItemClickListener = dialogItemClickListener;
	}

	public static interface OnDialogItemClickListener {
		/**
		 * the callback function for item click event
		 * 
		 * @param itemId
		 *            could be any one amongst: SHARE_TO_ICON_RENREN,
		 *            SHARE_TO_ICON_WEIXIN_SOCIAL_CIRCLE,
		 *            SHARE_TO_ICON_WEIXIN_CONTACTS, SHARE_TO_ICON_QQ,
		 *            SHARE_TO_ICON_WEIBO, DLG_TEXT_ITEM_CUSTOM_1,
		 *            DLG_TEXT_ITEM_CUSTOM_2, DLG_TEXT_ITEM_CUSTOM_3, (NOTE:
		 *            DLG_TEXT_ITEM_CANCEL would NOT be handled here, because it
		 *            would be handled by OnCancelListener.)
		 */
		public void onDialogItemClick(int itemId);
	}

}
