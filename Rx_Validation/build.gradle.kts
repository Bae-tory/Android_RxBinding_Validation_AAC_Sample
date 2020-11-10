buildscript {
    repositories {
        google()
        jcenter()

        dependencies {
            classpath("com.android.tools.build:gradle:${Versions.GRADLE}")
            classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN}")
            classpath("com.google.dagger:hilt-android-gradle-plugin:${Versions.HILT}")
        }
    }
}
allprojects {
    repositories {
        google()
        jcenter()
        maven { url = uri("https://maven.google.com/") }
    }
}


tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
