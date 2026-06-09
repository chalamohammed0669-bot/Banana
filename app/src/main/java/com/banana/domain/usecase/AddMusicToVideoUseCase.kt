package com.banana.domain.usecase

import com.banana.domain.repository.VideoRepository
import kotlinx.coroutines.flow.Flow

class AddMusicToVideoUseCase(private val videoRepository: VideoRepository) {
    operator fun invoke(videoId: String, musicId: String): Flow<Boolean> =
        videoRepository.addMusicToVideo(videoId, musicId)
}
