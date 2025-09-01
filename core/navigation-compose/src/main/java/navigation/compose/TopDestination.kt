package navigation.compose

/**
 * Represents a top-level navigation destination, typically used for bottom navigation tabs.
 *
 * Each [TopDestination] contains a nested navigation graph and exposes the start destination
 * of that graph for use in setting up navigation hosts.
 *
 * This interface extends [Destination] and is typically used to define main sections
 * of the app's navigation structure.
 */
interface TopDestination : Destination {

    /**
     * The nested navigation graph associated with this top-level destination.
     */
    val nestedGraph: NestedGraph

    /**
     * The start destination of the nested graph. Delegates to [NestedGraph.startDestination].
     */
    val startDestination: Destination
        get() = nestedGraph.startDestination
}
