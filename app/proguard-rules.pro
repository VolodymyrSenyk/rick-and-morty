#####################
### Common
#####################
# Repackage all class files by moving them into the single package. Make the processed code smaller
-repackageclasses

#####################
### Safe args arguments
#####################
-keepnames class * extends android.os.Parcelable
-keep class kotlinx.serialization.** { *; }
-keep enum * { *; }
