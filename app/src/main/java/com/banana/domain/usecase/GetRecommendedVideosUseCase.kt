package com.banana.domain.usecase

import com.banana.domain.model.Video
import com.banana.domain.repository.VideoRepository
import kotlinx.coroutines.flow.Flow

class GetRecommendedVideosUseCase(private val videoRepository: VideoRepository) {
    operator fun invoke(userId: String): Flow<List<Video>> =
        videoRepository.getRecommendedVideos(userId)
}
