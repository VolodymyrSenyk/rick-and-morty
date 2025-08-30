package navigation.compose

/**
 * Represents a nested navigation graph that defines a group of related destinations.
 *
 * Used for structuring complex navigation flows where multiple destinations are part
 * of a feature-specific subgraph.
 */
interface NestedGraph {
    /**
     * The start destination of current nested navigation graph.
     */
    val startDestination: Destination
}
