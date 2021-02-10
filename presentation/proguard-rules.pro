#####################
### Common
#####################
# Repackage all class files by moving them into the single package. Make the processed code smaller
-repackageclasses

#####################
### Gson
#####################
# Classes that interact with Gson
-keep class com.senyk.rickandmorty.presentation.presentation.entity.** { *; }
-keepclassmembers enum * { *; }
