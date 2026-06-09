package com.banana.domain.usecase

import com.banana.domain.model.Video
import com.banana.domain.repository.VideoRepository
import kotlinx.coroutines.flow.Flow

class DeleteVideoUseCase(private val videoRepository: VideoRepository) {
    operator fun invoke(videoId: String): Flow<Boolean> =
        videoRepository.deleteVideo(videoId)
}
