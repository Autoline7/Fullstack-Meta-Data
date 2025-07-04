package com.metaWebApp.MetaWebApp.model;


/**
 * Represents the type of Instagram data file declared by the user during upload.
 */
public enum DeclaredFileType {
    FOLLOWERS,       // For followers.json and following.json (usually in a ZIP)
    CLOSE_FRIENDS,   // For close_friends.json
    MESSAGES,        // For messages.json
    // Add more as you introduce new Instagram data types
    UNKNOWN
}
