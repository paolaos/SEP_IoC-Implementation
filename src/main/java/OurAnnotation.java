@interface OurAnnotation {
    String id();
    String name();
    String scope();
    String initMethod();
    String destroyMethod();
    String property();
}

@interface OurLittleAnnotation {
    String name();
    String type();
    String isReferenced();
    String value();
}
