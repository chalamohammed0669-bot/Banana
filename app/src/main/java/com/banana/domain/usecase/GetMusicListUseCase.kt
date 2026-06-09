package com.banana.domain.usecase

import com.banana.domain.model.Music
import com.banana.domain.repository.VideoRepository
import kotlinx.coroutines.flow.Flow

class GetMusicListUseCase(private val videoRepository: VideoRepository) {
    operator fun invoke(): Flow<List<Music>> =
        videoRepository.getMusicList()
}
