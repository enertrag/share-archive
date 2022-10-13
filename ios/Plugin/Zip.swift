import Foundation

@objc public class Zip: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
