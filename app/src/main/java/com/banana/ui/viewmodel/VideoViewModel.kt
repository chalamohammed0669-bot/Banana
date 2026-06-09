package com.banana.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.banana.domain.model.Video
import com.banana.domain.model.Music
import com.banana.domain.usecase.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VideoViewModel(
    private val uploadVideoUseCase: UploadVideoUseCase,
    private val getUserVideosUseCase: GetUserVideosUseCase,
    private val deleteVideoUseCase: DeleteVideoUseCase,
    private val editVideoUseCase: EditVideoUseCase,
    private val saveDraftUseCase: SaveDraftUseCase,
    private val getMusicListUseCase: GetMusicListUseCase,
    private val addMusicToVideoUseCase: AddMusicToVideoUseCase,
    private val getRecommendedVideosUseCase: GetRecommendedVideosUseCase
) : ViewModel() {

    private val _videoUploadState = MutableStateFlow<Boolean>(false)
    val videoUploadState: StateFlow<Boolean> = _videoUploadState.asStateFlow()

    private val _userVideos = MutableStateFlow<List<Video>>(emptyList())
    val userVideos: StateFlow<List<Video>> = _userVideos.asStateFlow()

    private val _musicList = MutableStateFlow<List<Music>>(emptyList())
    val musicList: StateFlow<List<Music>> = _musicList.asStateFlow()

    private val _recommendedVideos = MutableStateFlow<List<Video>>(emptyList())
    val recommendedVideos: StateFlow<List<Video>> = _recommendedVideos.asStateFlow()

    private val _draftVideos = MutableStateFlow<List<Video>>(emptyList())
    val draftVideos: StateFlow<List<Video>> = _draftVideos.asStateFlow()

    fun uploadVideo(video: Video, videoFile: ByteArray) {
        viewModelScope.launch {
            uploadVideoUseCase(video, videoFile).collect { success ->
                _videoUploadState.value = success
            }
        }
    }

    fun getUserVideos(userId: String) {
        viewModelScope.launch {
            getUserVideosUseCase(userId).collect { videos ->
                _userVideos.value = videos
            }
        }
    }

    fun deleteVideo(videoId: String) {
        viewModelScope.launch {
            deleteVideoUseCase(videoId).collect { success ->
                if (success) {
                    // Refresh videos
                    _userVideos.value = _userVideos.value.filter { it.id != videoId }
                }
            }
        }
    }

    fun editVideo(video: Video) {
        viewModelScope.launch {
            editVideoUseCase(video).collect { success ->
                if (success) {
                    // Update in list
                    _userVideos.value = _userVideos.value.map {
                        if (it.id == video.id) video else it
                    }
                }
            }
        }
    }

    fun saveDraft(video: Video) {
        viewModelScope.launch {
            saveDraftUseCase(video).collect { success ->
                if (success) {
                    _draftVideos.value = _draftVideos.value + video
                }
            }
        }
    }

    fun getMusicList() {
        viewModelScope.launch {
            getMusicListUseCase().collect { musicList ->
                _musicList.value = musicList
            }
        }
    }

    fun addMusicToVideo(videoId: String, musicId: String) {
        viewModelScope.launch {
            addMusicToVideoUseCase(videoId, musicId).collect { success ->
                if (success) {
                    // Update video with music
                    _userVideos.value = _userVideos.value.map { video ->
                        if (video.id == videoId) video.copy(musicId = musicId) else video
                    }
                }
            }
        }
    }

    fun getRecommendedVideos(userId: String) {
        viewModelScope.launch {
            getRecommendedVideosUseCase(userId).collect { videos ->
                _recommendedVideos.value = videos
            }
        }
    }
}
