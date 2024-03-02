package com.recon.filmaster.Utils

data class MovieModel(
    val id: Int,
    val title: String,
    val genreIds: List<Int>, // Обновлено на List<Int>
    val releaseYear: String,
    val rating: Double,
    val posterPath: String,
    val description : String
)

fun getGenreDescription(genreIds: List<Int>): String {
    val genreMap = mapOf(
        28 to "Боевик",
        12 to "Приключения",
        16 to "Мультфильм",
        35 to "Комедия",
        80 to "Криминал",
        99 to "Документальный",
        18 to "Драма",
        10751 to "Семейный",
        14 to "Фэнтези",
        36 to "Исторический",
        27 to "Ужасы",
        10402 to "Музыка",
        9648 to "Детектив",
        10749 to "Мелодрама",
        878 to "Фантастика",
        10770 to "Телевизионный фильм",
        53 to "Триллер",
        10752 to "Военный",
        37 to "Вестерн"
    )

    val genreDescriptions = mutableListOf<String>()
    for (genreId in genreIds) {
        genreMap[genreId]?.let { genreDescriptions.add(it) }
    }
    return genreDescriptions.joinToString(", ")
}