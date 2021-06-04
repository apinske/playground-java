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
        PlaygroundMobileAPI.credential = URLCredential(user: "apinske", password: "12345678", persistence: URLCredential.Persistence.forSession)
        ThingAPI.getThingsWithRequestBuilder().addCredential().execute { r in
            try! print(r.get().body![0])
        }
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
