package pizza.ecs.android;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;

public class PhotoMethodDialog extends DialogFragment {
    
    private static final int TAKE_IMAGE = 5464;
    private static final int PICK_IMAGE = 5345;
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add new pizza:")
               .setItems(new CharSequence[]{"Take photo", "Select from camera roll"}, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       Intent intent = new Intent();
                       int code;
                       if(which == 0) {
                           intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                           code = TAKE_IMAGE;
                           
                       } else {
                           intent.setType("image/*");
                           intent.setAction(Intent.ACTION_GET_CONTENT);
                           code = PICK_IMAGE;
                       }
                       
                       getActivity().startActivityForResult(intent, code);
                   }
               });
        return builder.create();
    }
}
