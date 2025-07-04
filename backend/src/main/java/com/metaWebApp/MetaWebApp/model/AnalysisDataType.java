package com.metaWebApp.MetaWebApp.model;

/**
 * Defines the specific type of data represented by an AnalysisResult entry.
 * This helps categorize and retrieve different kinds of analysis output.
 */
public enum AnalysisDataType {
    UNFOLLOWER,               // Represents an Instagram user who doesn't follow back
    CLOSE_FRIEND_ITEM,        // Represents an entry from the close_friends.json file
    MESSAGE_THREAD_SUMMARY,   // Represents a summary of an Instagram message thread
    // Add more types as you process different aspects of Instagram data
    LIKED_MEDIA_ITEM,         // If you later process liked posts
    COMMENT_ITEM              // If you later process comments
}
