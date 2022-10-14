import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(ZipPlugin)
public class ZipPlugin: CAPPlugin {

    @objc func isAvailable(_ call: CAPPluginCall) {
    
        call.resolve(["available": true])
    }
    
    @objc func shareArchive(_ call: CAPPluginCall) {
        
        let srcDir = call.getString("sourceDir") ?? ""
        let dstFilename = call.getString("destFilename") ?? ""
        
        let srcUrl = URL(string: srcDir)!
        
        
        let fm = FileManager.default
        
        DispatchQueue.global(qos: .background).async {

            var archiveUrl: URL?
            var error: NSError?
            let coordinator = NSFileCoordinator()
            
            // zip up the documents directory
            coordinator.coordinate(readingItemAt: srcUrl, options: [.forUploading], error: &error) { (zipUrl) in

                // zipUrl is only valid inside this block, so we have to
                // move the file to another temp location
        
                let tmpUrl = try! fm.url(
                    for: .itemReplacementDirectory,
                    in: .userDomainMask,
                    appropriateFor: zipUrl,
                    create: true
                ).appendingPathComponent(dstFilename)
                try! fm.moveItem(at: zipUrl, to: tmpUrl)
                
                // store the URL so we can use it outside the block
                archiveUrl = tmpUrl
            }
            
            if let archiveUrl = archiveUrl {
                
                let viewController = UIActivityViewController(activityItems: [archiveUrl], applicationActivities: nil)
               
                DispatchQueue.main.async {
                    self.bridge?.viewController!.present(viewController, animated: true)
                    DispatchQueue.main.async { call.resolve(["success": true]) }
                }
                
            } else if let error = error {

                CAPLog.print("⚡️", "an error has occurred", error)
                DispatchQueue.main.async { call.resolve(["success": false]) }
                
            } else {

                CAPLog.print("⚡️", "something went wrong")
                DispatchQueue.main.async { call.resolve(["success": false]) }

            }
        }
    }
}
