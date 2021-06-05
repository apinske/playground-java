//
//  PlaygroundMobileApp.swift
//  PlaygroundMobile
//
//  Created by Alexander Pinske on 05.06.21.
//

import SwiftUI

@main
struct PlaygroundMobileApp: App {
    
    init() {
        PlaygroundMobileAPI.basePath = "http://localhost:8080/playground-api"
        PlaygroundMobileAPI.credential = URLCredential(user: "apinske", password: "12345678", persistence: .none)
    }
    
    var body: some Scene {
        WindowGroup {
            ThingsView()
        }
    }
}
