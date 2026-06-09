package com.banana.domain.repository

import com.banana.domain.model.Message
import com.banana.domain.model.Chat
import kotlinx.coroutines.flow.Flow

interface MessagingRepository {
    fun sendMessage(message: Message): Flow<Boolean>
    fun getMessage(messageId: String): Flow<Message?>
    fun getChatMessages(chatId: String): Flow<List<Message>>
    fun deleteMessage(messageId: String): Flow<Boolean>
    fun markAsRead(messageId: String): Flow<Boolean>
    fun createChat(chat: Chat): Flow<Boolean>
    fun getChats(userId: String): Flow<List<Chat>>
    fun getChatById(chatId: String): Flow<Chat?>
    fun sendVoiceMessage(chatId: String, userId: String, audioFile: ByteArray): Flow<Boolean>
    fun sendMediaMessage(chatId: String, userId: String, mediaFile: ByteArray, mediaType: String): Flow<Boolean>
    fun getUnreadCount(userId: String): Flow<Int>
    fun markChatAsRead(chatId: String, userId: String): Flow<Boolean>
}
