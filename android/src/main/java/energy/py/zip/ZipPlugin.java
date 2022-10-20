package energy.py.zip;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import java.io.File;

import androidx.core.content.FileProvider;

@CapacitorPlugin(name = "Zip")
public class ZipPlugin extends Plugin {

    private Zip implementation = new Zip();

    @PluginMethod
    public void isAvailable(PluginCall call) {
        JSObject ret = new JSObject();
        ret.put("available", true);

        call.resolve(ret);
    }

    @PluginMethod
    public void shareArchive(PluginCall call) {

        String sourceDir = call.getString("sourceDir");
        String destFilename = call.getString("destFilename");

        if(sourceDir.toLowerCase().startsWith("file://")) {
            sourceDir = sourceDir.substring(7);
        }

        Context context = this.getContext();
        JSObject result = new JSObject();

        File file = implementation.zipDirectory(context, sourceDir, destFilename);
        if (file != null) {

            String packageName = this.getActivity().getApplicationContext().getPackageName();

            Uri uri = FileProvider.getUriForFile(getActivity(), packageName + ".fileprovider", file);

            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            shareIntent.setType("application/octet-stream");

            this.getActivity().startActivity(Intent.createChooser(shareIntent, null));

            result.put("success", true);

        } else {
            result.put("success", false);
        }

        call.resolve(result);
    }
}
