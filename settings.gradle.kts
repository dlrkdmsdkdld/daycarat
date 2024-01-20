pluginManagement {
    repositories {
//        maven(url ="https://plugins.gradle.org/m2/")
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
//        maven(url ="https://plugins.gradle.org/m2/")
        jcenter() // 关键是这个仓库！！！

        maven { url = uri("https://devrepo.kakao.com/nexus/content/groups/public/") }
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "daycarat"
include(":app")
