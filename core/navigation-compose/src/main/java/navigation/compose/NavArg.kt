package navigation.compose

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle

/**
 * Represents a typed argument used for passing data between navigation destinations.
 * Must implement [Parcelable] to be stored in [SavedStateHandle].
 */
interface NavArg : Parcelable
