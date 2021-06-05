//
//  ContentView.swift
//  PlaygroundMobile
//
//  Created by Alexander Pinske on 05.06.21.
//

import SwiftUI

struct ContentView: View {
    @State var things = [Thing]()
    
    var body: some View {
        List(things, id: \.id) { thing in
            Text(thing.name!)
        }.onAppear {
            ThingAPI.getThingsWithRequestBuilder().addCredential().execute { r in
                try! things = r.get().body!
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
