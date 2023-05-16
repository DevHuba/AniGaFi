package eu.devhuba.anigafi.model

data class AnimeApiResponse(
	val next_episode_at: String?,
	val anime: CurrentAnime?
)

data class CurrentAnime(
	val id: Int?,
	val name: String?,
	val russian: String?,
	val image: ImageRange?,
	val kind: String?,
	val score: String?,
	val status : String?,
	val episodes: String?,
	val episodes_aired: String?,
	val aired_on: String?
)

data class ImageRange(
	val original: String?
)

