package com.banana.data.repository

import com.banana.domain.model.Video
import com.banana.domain.model.Music
import com.banana.domain.repository.VideoRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.UUID

class VideoRepositoryImpl(
    private val firestore: FirebaseFirestore,
    private val firebaseStorage: FirebaseStorage
) : VideoRepository {

    override fun uploadVideo(video: Video, videoFile: ByteArray): Flow<Boolean> = callbackFlow {
        try {
            val videoId = UUID.randomUUID().toString()
            val storageRef = firebaseStorage.reference.child("videos/$videoId/video.mp4")
            
            storageRef.putBytes(videoFile)
                .addOnSuccessListener {
                    storageRef.downloadUrl.addOnSuccessListener { uri ->
                        val updatedVideo = video.copy(
                            id = videoId,
                            videoUrl = uri.toString(),
                            isPublished = true
                        )
                        saveVideoToFirestore(updatedVideo) { success ->
                            trySend(success)
                        }
                    }
                }
                .addOnFailureListener {
                    trySend(false)
                }
        } catch (e: Exception) {
            trySend(false)
        }
        awaitClose()
    }

    override fun getVideoById(videoId: String): Flow<Video?> = callbackFlow {
        try {
            firestore.collection("videos")
                .document(videoId)
                .get()
                .addOnSuccessListener { document ->
                    val video = document.toObject(Video::class.java)
                    trySend(video)
                }
                .addOnFailureListener {
                    trySend(null)
                }
        } catch (e: Exception) {
            trySend(null)
        }
        awaitClose()
    }

    override fun getUserVideos(userId: String): Flow<List<Video>> = callbackFlow {
        try {
            firestore.collection("videos")
                .whereEqualTo("userId", userId)
                .whereEqualTo("isPublished", true)
                .orderBy("createdAt")
                .get()
                .addOnSuccessListener { documents ->
                    val videos = documents.mapNotNull { it.toObject(Video::class.java) }
                    trySend(videos)
                }
                .addOnFailureListener {
                    trySend(emptyList())
                }
        } catch (e: Exception) {
            trySend(emptyList())
        }
        awaitClose()
    }

    override fun deleteVideo(videoId: String): Flow<Boolean> = callbackFlow {
        try {
            firestore.collection("videos")
                .document(videoId)
                .delete()
                .addOnSuccessListener {
                    trySend(true)
                }
                .addOnFailureListener {
                    trySend(false)
                }
        } catch (e: Exception) {
            trySend(false)
        }
        awaitClose()
    }

    override fun updateVideo(video: Video): Flow<Boolean> = callbackFlow {
        try {
            firestore.collection("videos")
                .document(video.id)
                .set(video)
                .addOnSuccessListener {
                    trySend(true)
                }
                .addOnFailureListener {
                    trySend(false)
                }
        } catch (e: Exception) {
            trySend(false)
        }
        awaitClose()
    }

    override fun saveDraft(video: Video): Flow<Boolean> = callbackFlow {
        try {
            val draftVideo = video.copy(isDraft = true, isPublished = false)
            firestore.collection("videos")
                .document(video.id.ifEmpty { UUID.randomUUID().toString() })
                .set(draftVideo)
                .addOnSuccessListener {
                    trySend(true)
                }
                .addOnFailureListener {
                    trySend(false)
                }
        } catch (e: Exception) {
            trySend(false)
        }
        awaitClose()
    }

    override fun getDrafts(userId: String): Flow<List<Video>> = callbackFlow {
        try {
            firestore.collection("videos")
                .whereEqualTo("userId", userId)
                .whereEqualTo("isDraft", true)
                .get()
                .addOnSuccessListener { documents ->
                    val videos = documents.mapNotNull { it.toObject(Video::class.java) }
                    trySend(videos)
                }
                .addOnFailureListener {
                    trySend(emptyList())
                }
        } catch (e: Exception) {
            trySend(emptyList())
        }
        awaitClose()
    }

    override fun publishVideo(videoId: String): Flow<Boolean> = callbackFlow {
        try {
            firestore.collection("videos")
                .document(videoId)
                .update("isPublished", true, "isDraft", false)
                .addOnSuccessListener {
                    trySend(true)
                }
                .addOnFailureListener {
                    trySend(false)
                }
        } catch (e: Exception) {
            trySend(false)
        }
        awaitClose()
    }

    override fun getAllVideos(): Flow<List<Video>> = callbackFlow {
        try {
            firestore.collection("videos")
                .whereEqualTo("isPublished", true)
                .orderBy("createdAt")
                .limit(100)
                .get()
                .addOnSuccessListener { documents ->
                    val videos = documents.mapNotNull { it.toObject(Video::class.java) }
                    trySend(videos)
                }
                .addOnFailureListener {
                    trySend(emptyList())
                }
        } catch (e: Exception) {
            trySend(emptyList())
        }
        awaitClose()
    }

    override fun getVideosByHashtag(hashtag: String): Flow<List<Video>> = callbackFlow {
        try {
            firestore.collection("videos")
                .whereArrayContains("hashtags", hashtag)
                .whereEqualTo("isPublished", true)
                .get()
                .addOnSuccessListener { documents ->
                    val videos = documents.mapNotNull { it.toObject(Video::class.java) }
                    trySend(videos)
                }
                .addOnFailureListener {
                    trySend(emptyList())
                }
        } catch (e: Exception) {
            trySend(emptyList())
        }
        awaitClose()
    }

    override fun getMusicList(): Flow<List<Music>> = callbackFlow {
        try {
            firestore.collection("music")
                .limit(50)
                .get()
                .addOnSuccessListener { documents ->
                    val musicList = documents.mapNotNull { it.toObject(Music::class.java) }
                    trySend(musicList)
                }
                .addOnFailureListener {
                    trySend(emptyList())
                }
        } catch (e: Exception) {
            trySend(emptyList())
        }
        awaitClose()
    }

    override fun getMusicById(musicId: String): Flow<Music?> = callbackFlow {
        try {
            firestore.collection("music")
                .document(musicId)
                .get()
                .addOnSuccessListener { document ->
                    val music = document.toObject(Music::class.java)
                    trySend(music)
                }
                .addOnFailureListener {
                    trySend(null)
                }
        } catch (e: Exception) {
            trySend(null)
        }
        awaitClose()
    }

    override fun addMusicToVideo(videoId: String, musicId: String): Flow<Boolean> = callbackFlow {
        try {
            firestore.collection("videos")
                .document(videoId)
                .update("musicId", musicId)
                .addOnSuccessListener {
                    trySend(true)
                }
                .addOnFailureListener {
                    trySend(false)
                }
        } catch (e: Exception) {
            trySend(false)
        }
        awaitClose()
    }

    override fun getRecommendedVideos(userId: String): Flow<List<Video>> = callbackFlow {
        try {
            // This would typically involve an ML-based recommendation engine
            firestore.collection("videos")
                .whereEqualTo("isPublished", true)
                .limit(50)
                .get()
                .addOnSuccessListener { documents ->
                    val videos = documents.mapNotNull { it.toObject(Video::class.java) }
                    trySend(videos)
                }
                .addOnFailureListener {
                    trySend(emptyList())
                }
        } catch (e: Exception) {
            trySend(emptyList())
        }
        awaitClose()
    }

    private fun saveVideoToFirestore(video: Video, callback: (Boolean) -> Unit) {
        firestore.collection("videos")
            .document(video.id)
            .set(video)
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }
}
