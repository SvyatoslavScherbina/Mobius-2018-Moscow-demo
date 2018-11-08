package sample.presenter

interface PermissionManager {
    suspend fun requestPermission(permission: String): Boolean
}