/** Some valid java code from Eric Blake.  Some of these constructions
 *  broke previous versions of the grammars.  These should all compile
 *  with any JLS2 javac, as well as parse correctly (no syntax errors)
 *  using the java12.cup/java14.cup/java15.cup grammars in this package. */
class Eric {
    // parenthesized variables on the left-hand-size of assignments
    // are legal according to JLS 2.  See comments on jikes bug 105
    //         http://www-124.ibm.com/developerworks/bugs/?func=detailbug&bug_id=105&group_id=10
    // for more details. According to Eric Blake:
    //       The 2nd edition JLS is weak on this point - the grammar
    //       in 15.26 prohibits assignments to parenthesized
    //       variables, but earlier in 15.8.5 it states that a
    //       parenthesized variable is still a variable (in JLS1, a
    //       parenthesized variable was a value), and the intent of
    //       assignment is that a variable appear on the left hand
    //       side.  Also, the grammar in chapter 18 (if you can call
    //       it such, because of its numerous typos and ambiguities)
    //       permits assignment to parenthesized variables.
    void m(int i) {
	(i) = 1;
    }
    // array access of an initialized array creation is legal; see Sun
    // bugs 4091602, 4321177:
    //   http://developer.java.sun.com/developer/bugParade/bugs/4091602.html
    //   http://developer.java.sun.com/developer/bugParade/bugs/4321177.html
    // Eric Blake says:
    //   Again, the body of the JLS prohibits this, but chapter 18 permits it.
    int i = new int[]{0}[0];
    int j = new char[] { 'O', 'K' }.length;

    // plain identifiers can qualify instance creation and explicit
    // constructors; see Sun bug 4750181:
    //   http://developer.java.sun.com/developer/bugParade/bugs/4750181.html
    // Eric Blake says:
    //   Sun admits the grammars between the earlier chapters and
    //   chapter 18 are incompatible, so they are not sure whether
    //   things like "identifier.new name()" should be legal or
    //   not. Chapter 18 treats identifiers as primaries, and javac
    //   compiles them.
    class B { };
    B b;
    void foo(Eric e) {
	e.b = e.new B();
    }
}
