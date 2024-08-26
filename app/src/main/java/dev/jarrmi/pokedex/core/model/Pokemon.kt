package dev.jarrmi.pokedex.core.model

import com.google.gson.annotations.SerializedName

data class Pokemon(
    val name: String,
    @SerializedName("url") val detailsUrl: String,
) {
    val listingImageUrl: String
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${
            detailsUrl.split(
                "/"
            ).dropLast(1).last()
        }.png"

    val formattedName: String
        get() = name.replaceFirstChar {
            if (it.isLowerCase()) {
                it.titlecase()
            } else {
                it.toString()
            }
        }.replace(oldValue = "-", newValue = " ")
}
