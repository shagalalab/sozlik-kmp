package com.shagalalab.sozlik.shared.domain.component.favorites

import com.arkivanov.decompose.ComponentContext

interface FavoritesComponent {
}

class FavoritesComponentImpl(componentContext: ComponentContext) :
    ComponentContext by componentContext,
    FavoritesComponent {

}
