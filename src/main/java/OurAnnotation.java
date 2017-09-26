/**
 * Annotations para las grapes
 */
@interface OurAnnotation {
    String id();
    String name();
    String scope();
    String initMethod();
    String destroyMethod();
    String property();
}

/**
 * Annotations para las seeds
 */
@interface OurLittleAnnotation {
    String name();
    String type();
    String isReferenced();
    String value();
}
