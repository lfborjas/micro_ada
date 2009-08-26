/** A valid JSR-14 Java program, which illustrates some corner-cases in
 * the 'smart lexer' lookahead implementation of the grammar.  It should
 * compile correctly using a JSR-14 javac, as well as parse correctly
 * (no syntax errors) using the java15.cup grammar in this package. */
public class Test15<X> {
    <T> Test15(T t) { }
    int a = 1, b = 2;
    C c1 = new C<Integer>(), c2 = new C<B>(), c3 = new C<B[]>();
    C<B> cc2 = c2;
    C<B[]> cc3 = c3;
    boolean d = a < b, e = a < b;
    int f[] = new int[5];
    boolean g = a < f[1];
    boolean h = ( a < f[1] );
    Object i0 = (A) cc3;
    Object  i = ( A < B[] > ) cc3;
    Object  j = ( A < B > ) cc2;
    Object  k = ( A < A < B[] > >) null;
    Object  kk= ( A < A < B[] >>) null;
    Test15<X>.H hh = null;
    {
	Test15<X>.H hhh = null;
	for (boolean l=a<b, m=a<b; a<b ; l=a<b, f[0]++)
	    a=a;
	for (;d;)
	    b=b;
	A<Integer> m = c1;
	if (m instanceof C<Integer>)
	    a=a;
	for (boolean n = m instanceof C<Integer>,
		 o = a<b,
		 p = cc3 instanceof C<B[]>;
	     cc3 instanceof C<B[]>;
	     n = m instanceof C<Integer>,
		 o = a<b,
		 p = cc3 instanceof C<B[]>)
	    b=b;
	for (;m instanceof C<Integer>;)
	    a=a;
	if (a < b >> 1)
	    ;
	Object o1 = new A<A<B>>(),
	    o2 = new A<A<A<B>>>(),
	    o3 = new A<A<D<B,A<B>>>>();

	// new, "explicit parameter" version of method invocation.
	A<Integer> aa = Test15.<A<Integer>>foo();
	/* although the spec says this should work:
           A<Integer> aa_ = <A<Integer>>foo();
	 * Neal Gafter has assured me that this is a bug in the spec.
	 * Type arguments are only valid after a dot. */

	// "explicit parameters" with constructor invocations.
	new <String> K<Integer>("xh"); // prototype 2.2 chokes on this.
	this.new <String> K<Integer>("xh");
    }
    
    static class A<T> { T t; }
    static class B { }
    static class C<T> extends A<T> { }
    static class D<A,B> { }
    static class E<X,Y extends A<X>> { }
    static interface F { }
    // wildcard bounds.
    static class G { A<? extends F> a; A<? super C<Integer>> b; }
    class H { }
    static class I extends A<Object[]> { }
    static class J extends A<byte[]> { }
    class K<Y> { <T>K(T t) { Test15.<T>foo(); } }

    static <T> T foo() { return null; }
}
