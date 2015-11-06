package com.android.customdialogdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
   private Button btn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btn=(Button)findViewById(R.id.show_diaLog);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				onShareImgClick();
			}
		});
		
	}

	private void onShareImgClick() {
		CommonContentShareToDlg dlg = new CommonContentShareToDlg(this);
		String[] menuItemStrings;
		menuItemStrings = new String[] {};
		dlg.setCustomItems(menuItemStrings);
		dlg.setDialogItemClickListener(shareToDlgListener);

		dlg.show();
	}

	private CommonContentShareToDlg.OnDialogItemClickListener shareToDlgListener = new CommonContentShareToDlg.OnDialogItemClickListener() {
		@Override
		public void onDialogItemClick(int itemId) {
           
		}
	};
}
