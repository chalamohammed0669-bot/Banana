package com.banana.domain.usecase

import com.banana.domain.model.Video
import com.banana.domain.repository.VideoRepository
import kotlinx.coroutines.flow.Flow

class EditVideoUseCase(private val videoRepository: VideoRepository) {
    operator fun invoke(video: Video): Flow<Boolean> =
        videoRepository.updateVideo(video)
}
