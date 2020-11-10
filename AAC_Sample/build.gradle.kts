buildscript {
    val kotlin_version by extra("1.3.72")
    repositories {
        google()
        jcenter()

        dependencies {
            classpath("com.android.tools.build:gradle:${Versions.GRADLE}")
            classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN}")
            classpath("com.google.dagger:hilt-android-gradle-plugin:${Versions.HILT}")
        }
    }
    dependencies {
        "classpath"("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
    }
}
allprojects {
    repositories {
        google()
        jcenter()
        maven { url = uri("https://jitpack.io") }
    }
}
tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
