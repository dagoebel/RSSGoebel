package mi.rssGoebel;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class MyDialogFragment extends DialogFragment {
 
	ProgressDialog mojProgressDialog;
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        
		 mojProgressDialog = new ProgressDialog(getActivity());
	     
		 mojProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
	     mojProgressDialog.setMessage("Aktualisiere RSS Feed...");
	     mojProgressDialog.setCancelable(false);
	     
	     return mojProgressDialog;
	     
	}
		
	public void setPostep(int values) {
		
		mojProgressDialog.setProgress(values);
		
	}
    
}