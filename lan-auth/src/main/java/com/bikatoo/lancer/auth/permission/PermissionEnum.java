package com.bikatoo.lancer.auth.permission;

public enum PermissionEnum {

    // 系统管理员
    SYSTEM_ADMIN,

    // 账号管理
    ACCOUNT_MANAGER,

    // 缓存管理
    CACHE_MANAGER,

    // 配置管理
    CONFIG_MANAGER,

    // 文件管理
    FILE_MANAGER,

    // 媒体合集管理
    MEDIA_COLLECTION_MANAGER,

    // 音频管理
    AUDIO_MANAGER,

    // 权限管理
    PERMISSION_MANAGER,

    // 标签管理
    TAG_MANAGER,

    // 用户管理
    USER_MANAGER,

    // 视频管理
    VIDEO_MANAGER,

    // 图片管理
    IMAGE_MANAGER,

    // 表演者管理
    PERFORMER_MANAGER,

    ;


  public static PermissionEnum build(String permissionStr) {
    for (PermissionEnum pe : PermissionEnum.values()) {
        if (pe.name().equals(permissionStr)) {
            return pe;
        }
    }
    return null;
  }
}
