package com.banana.domain.usecase

import com.banana.domain.model.Video
import com.banana.domain.repository.VideoRepository
import kotlinx.coroutines.flow.Flow

class UploadVideoUseCase(private val videoRepository: VideoRepository) {
    operator fun invoke(video: Video, videoFile: ByteArray): Flow<Boolean> =
        videoRepository.uploadVideo(video, videoFile)
}
