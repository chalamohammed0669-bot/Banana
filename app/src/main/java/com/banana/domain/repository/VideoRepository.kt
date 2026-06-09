package com.banana.domain.repository

import com.banana.domain.model.Video
import com.banana.domain.model.Music
import kotlinx.coroutines.flow.Flow

interface VideoRepository {
    fun uploadVideo(video: Video, videoFile: ByteArray): Flow<Boolean>
    fun getVideoById(videoId: String): Flow<Video?>
    fun getUserVideos(userId: String): Flow<List<Video>>
    fun deleteVideo(videoId: String): Flow<Boolean>
    fun updateVideo(video: Video): Flow<Boolean>
    fun saveDraft(video: Video): Flow<Boolean>
    fun getDrafts(userId: String): Flow<List<Video>>
    fun publishVideo(videoId: String): Flow<Boolean>
    fun getAllVideos(): Flow<List<Video>>
    fun getVideosByHashtag(hashtag: String): Flow<List<Video>>
    fun getMusicList(): Flow<List<Music>>
    fun getMusicById(musicId: String): Flow<Music?>
    fun addMusicToVideo(videoId: String, musicId: String): Flow<Boolean>
    fun getRecommendedVideos(userId: String): Flow<List<Video>>
}
