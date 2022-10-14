package energy.py.zip;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "Zip")
public class ZipPlugin extends Plugin {

    private Zip implementation = new Zip();

    @PluginMethod
    public void isAvailable(PluginCall call) {
        JSObject ret = new JSObject();
        ret.put("available", false);

        call.resolve(ret);
    }

    @PluginMethod
    public void shareArchive(PluginCall call) {
        call.reject("not yet implemented");
    }
}
