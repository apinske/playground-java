//
//  ThingsView.swift
//  PlaygroundMobile
//
//  Created by Alexander Pinske on 05.06.21.
//

import SwiftUI

struct ThingsView: View {
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

struct ThingsView_Previews: PreviewProvider {
    static var previews: some View {
        ThingsView()
    }
}
