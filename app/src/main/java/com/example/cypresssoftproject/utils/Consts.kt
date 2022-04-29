package com.example.cypresssoftproject.utils

const val STATUS_CODE_SUCCESS = 200
const val STATUS_CODE_TOKEN_EXPIRE = 401
const val STATUS_CODE_FAILURE = 202
const val STATUS_CODE_INTERNAL_ERROR = 500

object apiUrl {
    const val API_URL = "https://jsonplaceholder.typicode.com/"
}

object PrefUtilsConstants {

    const val DATABASE_NAME = "CypressSoftDatabase"

}

object AlbumTable {
    const val ALBUM_TABLE = "albumTable"
    const val ID = "id"
    const val USER_ID = "userId"
    const val TITLE = "title"
}

object ImageTable {
    const val IMAGE_TABLE = "imageTable"
    const val ID = "id"
    const val USER_ID = "userId"
    const val ALBUM_ID = "albumId"
    const val TITLE = "title"
    const val URL = "url"
    const val LOCAL_URL = "localUrl"
    const val THUMBNAIL_URL = "thumbnailUrl"
    const val LOCAL_THUMBNAIL_URL = "localThumbnailUrl"
}

object SingleImageTable {
    const val SINGLE_IMAGE_TABLE = "singleImageTable"
    const val ID = "id"
    const val USER_ID = "userId"
    const val ALBUM_ID = "albumId"
    const val TITLE = "title"
    const val URL = "url"
    const val LOCAL_URL = "localUrl"
    const val THUMBNAIL_URL = "thumbnailUrl"
    const val LOCAL_THUMBNAIL_URL = "localThumbnailUrl"
}